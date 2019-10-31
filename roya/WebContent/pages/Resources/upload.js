var DWObject;
function Dynamsoft_OnReady() {

			DWObject = Dynamsoft.WebTwainEnv.GetWebTwain('dwtcontrolContainer'); // Get the Dynamic Web TWAIN object that is embeded in the div with id 'dwtcontrolContainer'
			if (DWObject) {
				var count = DWObject.SourceCount; // Populate how many sources are installed in the system
			
				for (var i = 0; i < count; i++)
					document.getElementById("source").options.add(new Option(
							DWObject.GetSourceNameItems(i), i)); // Add the sources in a drop-down list
				document.getElementById("imgTypejpeg").checked = true;
							
			}
		}

		function AcquireImage() {
			if (DWObject) {
				DWObject
						.SelectSourceByIndex(document.getElementById("source").selectedIndex);
				DWObject.OpenSource();
				DWObject.IfDisableSourceAfterAcquire = true; // Scanner source will be disabled/closed automatically after the scan.
				DWObject.AcquireImage();
			}
		}

		//Callback functions for async APIs
		function OnSuccess() {
			console.log('successful');
		}

		function OnFailure(errorCode, errorString) {
			alert(errorString);
		}

		function LoadImage() {
			if (DWObject) {
				DWObject.IfShowFileDialog = true; // Open the system's file dialog to load image
				DWObject.LoadImageEx("", EnumDWT_ImageType.IT_ALL, OnSuccess,
						OnFailure); // Load images in all supported formats (.bmp, .jpg, .tif, .png, .pdf). sFun or fFun will be called after the operation
			}
		}

		// OnHttpUploadSuccess and OnHttpUploadFailure are callback functions.
		// OnHttpUploadSuccess is the callback function for successful uploads while OnHttpUploadFailure is for failed ones.
		function OnHttpUploadSuccess() {
			console.log('successful');
		}

		function OnHttpUploadFailure(errorCode, errorString, sHttpResponse) {
			alert(errorString + sHttpResponse);
		}

		function UploadImage() {
			if (DWObject) {
				// If no image in buffer, return the function
				if (DWObject.HowManyImagesInBuffer == 0)
					return;

				var strHTTPServer = "www.dynamsoft.com";//The name of the HTTP server. For example: "www.dynamsoft.com";
				var CurrentPathName = unescape(location.pathname);
				var CurrentPath = CurrentPathName.substring(0, CurrentPathName
						.lastIndexOf("/") + 1);
				var strActionPage = CurrentPath + "SaveToFile.aspx";
				DWObject.IfSSL = false; // Set whether SSL is used
				DWObject.HTTPPort = location.port == "" ? 80 : location.port;

				var Digital = new Date();
				var uploadfilename = Digital.getMilliseconds(); // Uses milliseconds according to local time as the file name

				// Upload the image(s) to the server asynchronously
				if (document.getElementById("imgTypejpeg").checked == true) {
					
					if (DWObject
							.GetImageBitDepth(DWObject.CurrentImageIndexInBuffer) == 1)
						//If so, convert the image to Gray
						DWObject
								.ConvertToGrayScale(DWObject.CurrentImageIndexInBuffer);
					//Upload image in JPEG
					DWObject.HTTPUploadThroughPost(strHTTPServer,
							DWObject.CurrentImageIndexInBuffer, strActionPage,
							uploadfilename + ".jpg", OnHttpUploadSuccess,
							OnHttpUploadFailure);
				} else if (document.getElementById("imgTypetiff").checked == true) {
					DWObject.HTTPUploadAllThroughPostAsMultiPageTIFF(
							strHTTPServer, strActionPage, uploadfilename
									+ ".tif", OnHttpUploadSuccess,
							OnHttpUploadFailure);
				} else if (document.getElementById("imgTypepdf").checked == true) {
					DWObject.HTTPUploadAllThroughPostAsPDF(strHTTPServer,
							strActionPage, uploadfilename + ".pdf",
							OnHttpUploadSuccess, OnHttpUploadFailure);
				}
			}
		}