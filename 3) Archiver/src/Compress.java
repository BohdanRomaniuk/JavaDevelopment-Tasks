import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compress
{
  public static void gzipFile(String from, String to) throws IOException
  {
    FileInputStream in = new FileInputStream(from);
    GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(to));
    byte[] buffer = new byte[4096];
    int bytes_read;
    while ((bytes_read = in.read(buffer)) != -1)
    {
      out.write(buffer, 0, bytes_read);
    }
    in.close();
    out.close();
  }
  
  public static void ungzipFile(String from, String to) throws IOException
  {
	GZIPInputStream in = new GZIPInputStream(new FileInputStream(from));
	OutputStream out = new FileOutputStream(to);

	byte[] buffer = new byte[1024];
	int length;
	while ((length = in.read(buffer)) > 0) 
	{
		out.write(buffer, 0, length);
	}

	in.close();
	out.close();
  }

  /*public static void zipDirectory(String dir, String zipfile) throws IOException, IllegalArgumentException 
  {
    File d = new File(dir);
    if (!d.isDirectory())
      throw new IllegalArgumentException("Compress: not a directory:  " + dir);
    String[] entries = d.list();
    byte[] buffer = new byte[4096];
    int bytes_read;

    System.out.println("ZIPLOC: "+zipfile);
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));

    for (int i = 0; i < entries.length; i++) {
      File f = new File(d, entries[i]);
      if (f.isDirectory())
        continue;
      FileInputStream in = new FileInputStream(f);
      ZipEntry entry = new ZipEntry(f.getPath());
      out.putNextEntry(entry);
      while ((bytes_read = in.read(buffer)) != -1)
        out.write(buffer, 0, bytes_read);
      in.close();
    }
    out.close();
  }*/
  
  public static void zipDirectory(String from, String to)
  {
	  ZipDirectory z = new ZipDirectory();
	  z.zipDirectory(new File(from),to);
  }
  
  public static void unzipDirectory(String zipfile, String outputDir) throws IOException, IllegalArgumentException 
  {
	byte[] buffer = new byte[1024];
	File folder = new File(outputDir);
	if(!folder.exists()){
		folder.mkdir();
	}

	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile));
	ZipEntry ze = zis.getNextEntry();
	while(ze!=null)
	{
		String fileName = ze.getName();
		File newFile = new File(outputDir + File.separator + fileName);    
	    System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	    new File(newFile.getParent()).mkdirs();
	    FileOutputStream fos = new FileOutputStream(newFile);             

	    int len;
	    while ((len = zis.read(buffer)) > 0)
	    {
	    	fos.write(buffer, 0, len);
	    }		
	    fos.close();   
	    ze = zis.getNextEntry();
	}
	zis.closeEntry();
	zis.close();
	    		
	System.out.println("Unziping of directory done");
  }
  
  public static void addFilesToZip(File source, File[] files){
	    try{
	        File tmpZip = File.createTempFile(source.getName(), null);
	        tmpZip.delete();
	        if(!source.renameTo(tmpZip)){
	            throw new Exception("Could not make temp file (" + source.getName() + ")");
	        }
	        byte[] buffer = new byte[4096];
	        ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(source));
	        for(int i = 0; i < files.length; i++){
	            InputStream in = new FileInputStream(files[i]);
	            out.putNextEntry(new ZipEntry(files[i].getName()));
	            for(int read = in.read(buffer); read > -1; read = in.read(buffer)){
	                out.write(buffer, 0, read);
	            }
	            out.closeEntry();
	            in.close();
	        }
	        for(ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()){
	            if(!zipEntryMatch(ze.getName(), files)){
	                out.putNextEntry(ze);
	                for(int read = zin.read(buffer); read > -1; read = zin.read(buffer)){
	                    out.write(buffer, 0, read);
	                }
	                out.closeEntry();
	            }
	        }
	        out.close();
	        tmpZip.delete();
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	}

	private static boolean zipEntryMatch(String zeName, File[] files){
	    for(int i = 0; i < files.length; i++){
	        if((files[i].getName()).equals(zeName)){
	            return true;
	        }
	    }
	    return false;
	}
}

class ZipDirectory {
	List<String> filesListInDir = new ArrayList<String>();
	  
	  private void populateFilesList(File dir) throws IOException {
	      File[] files = dir.listFiles();
	      for(File file : files){
	          if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
	          else populateFilesList(file);
	      }
	  }
	  
	  public void zipDirectory(File dir, String zipDirName) {
	      try {
	          populateFilesList(dir);
	          FileOutputStream fos = new FileOutputStream(zipDirName);
	          ZipOutputStream zos = new ZipOutputStream(fos);
	          for(String filePath : filesListInDir){
	              System.out.println("Zipping "+filePath);
	              ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
	              zos.putNextEntry(ze);
	              FileInputStream fis = new FileInputStream(filePath);
	              byte[] buffer = new byte[1024];
	              int len;
	              while ((len = fis.read(buffer)) > 0) {
	                  zos.write(buffer, 0, len);
	              }
	              zos.closeEntry();
	              fis.close();
	          }
	          zos.close();
	          fos.close();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }
}