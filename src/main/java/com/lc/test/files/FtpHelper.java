package com.lc.test.files;


import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Function;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/**
 *
 * @author xxj
 */
public class FtpHelper implements Closeable {
	private FTPClient ftp = null;
	boolean _isLogin = false;
	public static FtpHelper getInstance() {
		return new FtpHelper();
	}

	/**
	 *
	 * ftp 匿名登录
	 * @param ip ftp服务地址
	 * @param port 端口号
	 */
	public boolean login(String ip,int port){
		//如果没有设置ftp用户可将username设为anonymous，密码为任意字符串
		return login(ip, port,"anonymous","");
	}
	/**
	 *
	 * ftp登录
	 * @param ip ftp服务地址
	 * @param port 端口号
	 * @param uname 用户名
	 * @param pass 密码
	 */
	public boolean login(String ip,int port, String uname, String pass) {
		ftp = new FTPClient();
//		boolean flag=false;
		try {
			// 连接
			ftp.connect(ip,port);
			_isLogin = ftp.login(uname, pass);
			Debug.printFormat("ftp：{0}",_isLogin?"登录成功":"登录失败");
			// 检测连接是否成功
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				System.err.println("FTP服务器拒绝连接 ");
				return false;
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * 上传后触发
	 */
	public Function<FtpFileInfo, Boolean> onUploadFileAfter;

	/**
	 *
	 * ftp上传文件
	 * @param localFileName 待上传文件
	 * @param ftpDirName ftp 目录名
	 * @param ftpFileName ftp目标文件
	 * @return true||false
	 */
	public boolean uploadFile(String localFileName
			,String ftpDirName
			, String ftpFileName) {
		return uploadFile(localFileName, ftpDirName, ftpFileName,false);
	}
	/**
	 *
	 * ftp上传文件
	 * @param localFileName 待上传文件
	 * @param ftpDirName ftp 目录名
	 * @param ftpFileName ftp目标文件
	 * @param deleteLocalFile 是否删除本地文件
	 * @return true||false
	 */
	public boolean uploadFile(String localFileName
			, String ftpDirName
			, String ftpFileName
			, boolean deleteLocalFile) {


		Debug.printFormat("准备上传 [{0}] 到 ftp://{1}/{2}"
				,localFileName
				,ftpDirName
				,ftpFileName);
//		if(StringExtend.isNullOrEmpty(ftpDirName))
//			ftpDirName="/";
		if(StringExtend.isNullOrEmpty(ftpFileName))
			throw new RuntimeException("上传文件必须填写文件名！");

		File srcFile = new File(localFileName);
		if(!srcFile.exists())
			throw new RuntimeException("文件不存在："+localFileName);

		try (FileInputStream fis = new FileInputStream(srcFile)) {
			//上传文件
			boolean flag = uploadFile(fis,ftpDirName,ftpFileName);
			//上传前事件
			if(onUploadFileAfter!=null){
				onUploadFileAfter.apply(new FtpFileInfo(localFileName,ftpDirName,ftpFileName));
			}
			//删除文件
			if(deleteLocalFile){
				srcFile.delete();
				Debug.printFormat("ftp删除源文件：{0}",srcFile);
			}
			fis.close();
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
		}
	}


	/**
	 *
	 * ftp上传文件 (使用inputstream)
	 * @param localFileName 待上传文件
	 * @param ftpDirName ftp 目录名
	 * @param ftpFileName ftp目标文件
	 * @return true||false
	 */
	public boolean uploadFile(FileInputStream uploadInputStream
			,String ftpDirName
			, String ftpFileName) {
		Debug.printFormat("准备上传 [流] 到 ftp://{0}/{1}"
				,ftpDirName
				,ftpFileName);
//		if(StringExtend.isNullOrEmpty(ftpDirName))
//			ftpDirName="/";
		if(StringExtend.isNullOrEmpty(ftpFileName))
			throw new RuntimeException("上传文件必须填写文件名！");

		try {
			// 设置上传目录(没有则创建)
			if(!createDir(ftpDirName)){
				throw new RuntimeException("切入FTP目录失败："+ftpDirName);
			}
			ftp.setBufferSize(1024);
			//解决上传中文 txt 文件乱码
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");


			// 设置文件类型（二进制）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 上传
			String fileName = new String(ftpFileName.getBytes("GBK"),"iso-8859-1");
			if(ftp.storeFile(fileName, uploadInputStream)){
				uploadInputStream.close();
				Debug.printFormat("文件上传成功：{0}/{1}"
						,ftpDirName
						,ftpFileName);
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
		}
	}
	/**
	 * 下载文件
	 * @param ftpDirName ftp目录名
	 * @param ftpFileName ftp文件名
	 * @param localFileFullName 本地文件名
	 * @return
	 *  @author xxj
	 */
	public boolean downloadFile(String ftpDirName,
								String ftpFileName, String localFileFullName) {
		try {
			if("".equals(ftpDirName))
				ftpDirName="/";
			String dir = new String(ftpDirName.getBytes("GBK"),"iso-8859-1");
			if(!ftp.changeWorkingDirectory(dir)){
				System.out.println("切换目录失败："+ftpDirName);
				return false;
			}
			FTPFile[] fs = ftp.listFiles();
			String fileName = new String(ftpFileName.getBytes("GBK"),"iso-8859-1");
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					FileOutputStream is = new FileOutputStream(new File(localFileFullName));
					ftp.retrieveFile(ff.getName(), is);
					is.close();
					System.out.println("下载ftp文件已下载："+localFileFullName);
					return true;
				}
			}
			System.out.println("下载ftp文件失败："+ftpFileName+";目录："+ftpDirName);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 *
	 * 删除ftp上的文件
	 *
	 * @param ftpFileName
	 * @return true || false
	 */
	public boolean removeFile(String ftpFileName) {
		boolean flag = false;
		Debug.printFormat("待删除文件：{0}"
				,ftpFileName);
		try {
			ftpFileName = new String(ftpFileName.getBytes("GBK"),"iso-8859-1");
			flag = ftp.deleteFile(ftpFileName);
			Debug.printFormat("删除文件：[{0}]"
					,flag?"成功":"失败");
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 删除空目录
	 * @param dir
	 * @return
	 */
	public boolean removeDir(String dir){
		if(StringExtend.startWith(dir, "/"))
			dir="/"+dir;
		try {
			String d = new String(dir.toString().getBytes("GBK"),"iso-8859-1");
			return ftp.removeDirectory(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 创建目录(有则切换目录，没有则创建目录)
	 * @param dir
	 * @return
	 */
	public boolean createDir(String dir){
		if(StringExtend.isNullOrEmpty(dir))
			return true;
		String d;
		try {
			//目录编码，解决中文路径问题
			d = new String(dir.toString().getBytes("GBK"),"iso-8859-1");
			//尝试切入目录
			if(ftp.changeWorkingDirectory(d))
				return true;
			dir = StringExtend.trimStart(dir, "/");
			dir = StringExtend.trimEnd(dir, "/");
			String[] arr =  dir.split("/");
			StringBuffer sbfDir=new StringBuffer();
			//循环生成子目录
			for(String s : arr){
				sbfDir.append("/");
				sbfDir.append(s);
				//目录编码，解决中文路径问题
				d = new String(sbfDir.toString().getBytes("GBK"),"iso-8859-1");
				//尝试切入目录
				if(ftp.changeWorkingDirectory(d))
					continue;
				if(!ftp.makeDirectory(d)){
					System.out.println("[失败]ftp创建目录："+sbfDir.toString());
					return false;
				}
				System.out.println("[成功]创建ftp目录："+sbfDir.toString());
			}
			//将目录切换至指定路径
			return ftp.changeWorkingDirectory(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	/**
	 *
	 * 销毁ftp连接
	 *
	 */
	private void closeFtpConnection() {
		_isLogin = false;
		if (ftp != null) {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 *
	 * 销毁ftp连接
	 *
	 */
	@Override
	public void close() {
		this.closeFtpConnection();
	}

	public static class FtpFileInfo{
		public FtpFileInfo(String srcFile,String ftpDirName,String ftpFileName){
			this.ftpDirName=ftpDirName;
			this.ftpFileName=ftpFileName;
			this.srcFile=srcFile;
		}
		String srcFile;
		String ftpDirName;
		String ftpFileName;
		String ftpFileFullName;

		public String getSrcFile() {
			return srcFile;
		}
		public void setSrcFile(String srcFile) {
			this.srcFile = srcFile;
		}
		public String getFtpDirName() {
			return ftpDirName;
		}
		public void setFtpDirName(String ftpDirName) {
			this.ftpDirName = ftpDirName;
		}
		public String getFtpFileName() {
			return ftpFileName;
		}
		public void setFtpFileName(String ftpFileName) {
			this.ftpFileName = ftpFileName;
		}
		/**
		 * 获取ftp上传文件的完整路径名
		 * @return
		 *  @author xxj
		 */
		public String getFtpFileFullName() {
			return PathExtend.Combine("/",ftpDirName,ftpFileName);
		}

	}
}