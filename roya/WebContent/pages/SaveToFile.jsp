<%@  page language="java" import="java.io.*,java.util.*"%><%!
%><%
// 	// Create a factory for disk-based file items
// 	DiskFileItemFactory factory = new DiskFileItemFactory();

// 	// Configure a repository (to ensure a secure temp location is used)
// 	ServletContext servletContext = this.getServletConfig().getServletContext();
// 	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
// 	factory.setRepository(repository);


// 	// Set factory constraints
// 	factory.setSizeThreshold(1000000000);// Sets the size threshold beyond which files are written directly to disk.

// 	// Create a new file upload handler
// 	ServletFileUpload upload = new ServletFileUpload(factory);

// 	// Set overall request size constraint
// 	upload.setSizeMax(-1);

// 	// Parse the request
// 	List<FileItem> items = upload.parseRequest(request);

// 	// Process the uploaded items
// 	Iterator<FileItem> iter = items.iterator();
// 	while (iter.hasNext()) {
// 		FileItem item = iter.next();
// 		// Process a regular form field
// 		if (item.isFormField()) {
// 			//Do nothing if it's not a file
// 		} 
// 		// Process a file upload
// 		else {
// 			String fieldName = item.getFieldName();
// 			String fileName = item.getName();
// 			String contentType = item.getContentType();
// 			boolean isInMemory = item.isInMemory();
// 			long sizeInBytes = item.getSize();
// 			if(fileName!=null && sizeInBytes!=0){	
// 				//String path = application.getContextPath();//.getRealPath(request.getRequestURI());
// 				String path = request.getContextPath();
// 				String path2 = request.getPathInfo();
				
// 				String dir = new java.io.File(path).getParent();
// 				String servletPath = request.getServletPath();
// 				String realPath = request.getSession().getServletContext().getRealPath(servletPath);
// 				System.out.println("path :"+path);
// 				System.out.println("path2 :"+path2);
// 				System.out.println("dir :"+dir);
// 				System.out.println("servletPath :"+servletPath);
// 				System.out.println("realPath :"+realPath);
// 				File uploadedFile = new File("D:/environment/apache-tomcat-7.0.64/webapps/data/" + fileName);
// 				if(!uploadedFile.exists())
// 				{
// 					boolean result = uploadedFile.createNewFile();
// 					System.out.println("File create result:"+result);
// 				}			
// 				try {
// 					item.write(uploadedFile);
// 					out.println("DWTBarcodeUploadSuccess:" + fileName);
// 				} 
// 				catch (Exception e) {
// 					e.printStackTrace();
// 				}
// 			}
// 		}
// 	}
%>