import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

  public static void zipDirectory(String dir, String zipfile) throws IOException, IllegalArgumentException 
  {
    File d = new File(dir);
    if (!d.isDirectory())
      throw new IllegalArgumentException("Compress: not a directory:  " + dir);
    String[] entries = d.list();
    byte[] buffer = new byte[4096];
    int bytes_read;

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
}