<%@  page language="java" import="java.io.*,java.util.*,org.apache.commons.fileupload.*,org.apache.commons.fileupload.disk.*,org.apache.commons.fileupload.servlet.*"%><%!
%><%
	// Create a factory for disk-based file items
	DiskFileItemFactory factory = new DiskFileItemFactory();

	// Configure a repository (to ensure a secure temp location is used)
	ServletContext servletContext = this.getServletConfig().getServletContext();
	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	factory.setRepository(repository);


	// Set factory constraints
	factory.setSizeThreshold(1000000000);// Sets the size threshold beyond which files are written directly to disk.

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	// Set overall request size constraint
	upload.setSizeMax(-1);

	// Parse the request
	List<FileItem> items = upload.parseRequest(request);

	// Process the uploaded items
	Iterator<FileItem> iter = items.iterator();
	String _fields = "";
	String fileName = "";
	long sizeInBytes = 0;
	String path = application.getRealPath("/").replace('\\', '/');;//application.getRealPath(request.getRequestURI());
	//String dir = new java.io.File(path);
	String _temp_Name = path + "pages/action/Dynamsoft_Upload";
	out.println("*************"+_temp_Name);
	File ff = new File(_temp_Name);
	File _fieldsTXT = new File(_temp_Name+"/_temp_fields_tochange_later");
	if(!ff.exists())
	{
		ff.mkdirs();
		boolean result = _fieldsTXT.createNewFile();
		System.out.println("File create result:"+result);
	}
	Writer objWriter = new BufferedWriter(new FileWriter(_fieldsTXT));
	while (iter.hasNext()) {
		FileItem item = iter.next();
		// Process a regular form field
		if (item.isFormField()) {
			_fields = "FieldsTrue:";			
			String fieldName = item.getFieldName();
			String value = item.getString();			
			try {
				//File appending
				objWriter.write(fieldName + " :  " + value);
				objWriter.write(System.getProperty( "line.separator" ));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}			
		} 
		// Process a file upload
		else {
			if(_fields == "FieldsTrue:"){
				objWriter.flush();
				objWriter.close();	
			}
			else{
				objWriter.flush();
				objWriter.close();
				_fieldsTXT.delete();
			}
			String fieldName = item.getFieldName();
			fileName = item.getName();
			String contentType = item.getContentType();
			boolean isInMemory = item.isInMemory();
			sizeInBytes = item.getSize();
			if(fileName!=null && sizeInBytes!=0){
				File uploadedFile = new File(path + "pages/action/Dynamsoft_Upload/" + fileName);
				if(!uploadedFile.exists())
				{
					boolean result = uploadedFile.createNewFile();
					System.out.println("File create result:"+result);
				}			
				try {
					item.write(uploadedFile);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				if(_fieldsTXT.exists())
				{
					_fieldsTXT.renameTo(new File(path + "pages/action/Dynamsoft_Upload/" + fileName.substring(0,fileName.length()-4) + "_1.txt"));
				}
			}
		}
	}		
	Integer _fileSize = (int)(sizeInBytes/1024);
	String _strSize = Integer.toString(_fileSize) + "KB";
	out.println(_fields +"DWTUploadFileName:" + fileName + "UploadedFileSize:" + _strSize);
%>