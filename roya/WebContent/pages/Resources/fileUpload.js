
        var DWObject, CurrentPath, strHTTPServer;

        function Dynamsoft_OnReady() {
			strHTTPServer = "http://10.27.10.69:8080/"; //location.hostname;
			var CurrentPathName = unescape(location.pathname);
			CurrentPath = CurrentPathName.substring(0, CurrentPathName.lastIndexOf("/") + 1);
            DWObject = Dynamsoft.WebTwainEnv.GetWebTwain('dwtcontrolContainer'); // Get the Dynamic Web TWAIN object that is embeded in the div with id 'dwtcontrolContainer'
            if (DWObject) {
				DWObject.Height = 553;
				DWObject.Width = 693;
				DWObject.RegisterEvent('OnInternetTransferPercentage', function (sPercentage) {
					console.log(sPercentage);
				});
                var count = DWObject.SourceCount; // Populate how many sources are installed in the system
                for (var i = 0; i < count; i++)
                    document.getElementById("source").options.add(new Option(DWObject.GetSourceNameItems(i), i));  // Add the sources in a drop-down list
                document.getElementById("imgTypejpeg").checked = true;
            }
        }

        function AcquireImage() {
            if (DWObject) {
                DWObject.SelectSourceByIndex(document.getElementById("source").selectedIndex);
                DWObject.OpenSource();
                DWObject.IfDisableSourceAfterAcquire = true;	// Scanner source will be disabled/closed automatically after the scan.
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
                DWObject.LoadImageEx("", EnumDWT_ImageType.IT_ALL, OnSuccess, OnFailure); // Load images in all supported formats (.bmp, .jpg, .tif, .png, .pdf). sFun or fFun will be called after the operation
            }
        }

        function OnHttpUploadSuccess() {
            console.log('successful');
        }

        function OnHttpServerReturnedSomething(errorCode, errorString, sHttpResponse) {
			var textFromServer = sHttpResponse;
			if(textFromServer.indexOf('DWTBarcodeUploadSuccess') != -1)
			{
				var url = 'http://' + location.hostname + ':' + location.port + CurrentPath + 'UploadedImages/' + textFromServer.substr(24);
				document.getElementById('uploadedFile').innerHTML = "Uploaded File: <a href='" + url + "' target='_blank'>" + textFromServer.substr(24) + "</a>";
			}
        }

        function UploadImage() {
            if (DWObject) {
                // If no image in buffer, return the function
				DWObject.IfShowCancelDialogWhenImageTransfer = !document.getElementById('quietScan').checked;
                if (DWObject.HowManyImagesInBuffer == 0)
                    return;
                var strActionPage = CurrentPath + "SaveToFile.jsp";
                console.log("CurrentPath	:" +CurrentPath);
                DWObject.IfSSL = false; // Set whether SSL is used
                DWObject.HTTPPort = 8080;//location.port == "" ? 80 : location.port;
                strHTTPServer="http://localhost:8080/";
                var Digital = new Date();
                var uploadfilename = Digital.getMilliseconds(); // Uses milliseconds according to local time as the file name

                // Upload the image(s) to the server asynchronously
                if (document.getElementById("imgTypejpeg").checked == true) {
                    DWObject.HTTPUploadThroughPost(strHTTPServer, DWObject.CurrentImageIndexInBuffer, strActionPage, uploadfilename + ".jpg", OnHttpUploadSuccess, OnHttpServerReturnedSomething);
                }
                else if (document.getElementById("imgTypetiff").checked == true) {
                    DWObject.HTTPUploadAllThroughPostAsMultiPageTIFF(strHTTPServer, strActionPage, uploadfilename + ".tif", OnHttpUploadSuccess, OnHttpServerReturnedSomething);
                }
                else if (document.getElementById("imgTypepdf").checked == true) {
                    DWObject.HTTPUploadAllThroughPostAsPDF(strHTTPServer, strActionPage, uploadfilename + ".pdf", OnHttpUploadSuccess, OnHttpServerReturnedSomething);
                }
            }
        }