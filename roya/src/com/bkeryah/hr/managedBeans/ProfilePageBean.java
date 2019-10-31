package com.bkeryah.hr.managedBeans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialBlob;

import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.WrkInboxFolder;
import com.bkeryah.entities.WrkProfile;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ProfilePageBean {

	private ArcUsers user;
	private HrsMasterFile hrsMasterFile;
	private HrsEmpHistorical empHistoricalInfo;
	private boolean empFlag;
	private StreamedContent myImage;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private boolean systemPassPanelFlag;
	private boolean signPassPanelFlag;

	private String signNewPassword;
	private String systemNewPassword;

	private String hahsedSignPassOld;
	private String hashedSystemPassOld;

	private String hashedPassword;
	private String hashedNewSignPassword;
	private String hashedNewSystemPassword;

	private String systemPassOld;
	private String signPassOld;

	private String confirmSignPass;
	private String confirmSystemPass;
	private StreamedContent streamImage;
	private WrkInboxFolder folderInfo = new WrkInboxFolder();

	@PostConstruct
	public void init() {
		user = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, Utils.findCurrentUser().getUserId());

		if (user.getEmployeeNumber() != null) {
			setEmpFlag(true);
			hrsMasterFile = (HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
					user.getEmployeeNumber());
			setEmpHistoricalInfo(dataAccessService.getEmployeeLastHistoryRecord(user.getEmployeeNumber()));

		}

		byte[] myPicture = dataAccessService.loadArcPeoplePic(2125472791L);
		WrkProfile wrkProfile = user.getWrkProfile();
		if (myPicture != null && myPicture.length > 0) {
			InputStream inputStream = new ByteArrayInputStream(myPicture);
			if (inputStream != null) {
				try {
					byte[] pBlob = IOUtil.toByteArray(inputStream);
					Blob bild;
					bild = new SerialBlob(pBlob);
					InputStream stream = bild.getBinaryStream();
					streamImage = new DefaultStreamedContent(stream, "image/jpg");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				try {
					if (wrkProfile != null) {
						InputStream inputStream2 = new ByteArrayInputStream(wrkProfile.getSign2());
						byte[] buffer = new byte[inputStream2.available()];
						inputStream.read(buffer);

						File targetFile = new File("img.jpeg");
						@SuppressWarnings("resource")
						OutputStream outStream = new FileOutputStream(targetFile);
						outStream.write(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			if (wrkProfile != null) {
				InputStream inputStream3 = new ByteArrayInputStream(wrkProfile.getSign2());
				myImage = new DefaultStreamedContent(inputStream3, "image/jpeg");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public StreamedContent getStreamedcontent() {
		byte[] myPicture = dataAccessService.loadArcPeoplePic(2125472791L);// user.getWrkProfile().getSign2();
																			// //healthCertificate.getArcPeople().getNationalId());//Integer.parseInt(id));

		InputStream inputStream = new ByteArrayInputStream(myPicture);
		try {
			byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			return new DefaultStreamedContent(stream, "image/jpg");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

		// return new
		// DefaultStreamedContent(selectedTrdApplication.getLicTrdPic(),
		// "image/jpeg");
	}

	public void addNewFolder() {

		try {

			if (folderInfo.getFolderName() != null && !folderInfo.getFolderName().isEmpty()) {
				folderInfo.setFolderUserId(user.getUserId());
				folderInfo.setVisible('Y');
				dataAccessService.addFolderForUser(folderInfo);
				MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ طلبك بنجاح");
				folderInfo = new WrkInboxFolder();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"    يرجى ادخال اسم المجلد", "    يرجى ادخال اسم المجلد"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "خطا فى تنفيذ  طلبك", "خطا فى تنفيذ  طلبك"));
		}

	}

	public void saveNewSignPass() {

		try {
			hahsedSignPassOld = dataAccessService.getHashedPassword(user.getLoginName(), signPassOld);

			if (hahsedSignPassOld.equals(user.getWrkProfile().getSignPassword())) {
				try {
					if (signNewPassword.equals(confirmSignPass)) {
						// user.getWrkProfile().setSign3(sign3);
						dataAccessService.updateUserPasswordForSign(user.getLoginName(), user.getUserId(),
								signNewPassword);
						setSignPassPanelFlag(false);
						user = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
								Utils.findCurrentUser().getUserId());
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"تم تعديل كلمنة المرور للتواقيع الخاصة بك  ",
										"تم تعديل كلمنة المرور للتواقيع الخاصة بك "));
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" يرجى تاكيد كلمة المرور بشكل صحيح", " يرجى تاكيد كلمة المرور بشكل صحيح"));
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"خطا فى تعديل البيانات ", "خطا فى تعديل البيانات "));
					e.printStackTrace();
				}
			} else {
				System.out.println("current hashed password:" + user.getWrkProfile().getSignPassword()
						+ "pass that user enter :" + hahsedSignPassOld);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"يرجى ادخال كلمة المرور الحالية بشكل صحيح  ", "يرجى ادخال كلمة المرور الحالية بشكل صحيح "));

			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "خطا فى تشفير البيانات  ", "خطا فى تشفير البيانات "));
			e.printStackTrace();
		}
	}

	public void saveNewSystemPass() {

		try {
			hashedSystemPassOld = dataAccessService.getHashedPassword(user.getLoginName().toUpperCase(), systemPassOld);

			if (hashedSystemPassOld.equals(user.getPassword())) {
				try {
					if (systemNewPassword.equals(confirmSystemPass)) {
						dataAccessService.updateUserPasswordForSystem(user.getLoginName(), user.getUserId(),
								systemNewPassword);
						setSystemPassPanelFlag(false);
						user = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
								Utils.findCurrentUser().getUserId());
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								"تم تعديل كلمنة المرور للنظام الخاصة بك  ", "تم تعديل كلمنة المرور للنظام الخاصة بك "));
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" يرجى تاكيد كلمة المرور بشكل صحيح", " يرجى تاكيد كلمة المرور بشكل صحيح"));
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"خطا فى تعديل البيانات ", "خطا فى تعديل البيانات "));
					e.printStackTrace();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"يرجى ادخال كلمة المرور الحالية بشكل صحيح  ", "يرجى ادخال كلمة المرور الحالية بشكل صحيح "));

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "خطا فى تشفير البيانات  ", "خطا فى تشفير البيانات "));
			e.printStackTrace();
		}
	}

	public void updateSystemPassword() {
		setSignPassPanelFlag(false);
		setSystemPassPanelFlag(true);

	}

	public void updateSignPassword() {
		setSystemPassPanelFlag(false);
		setSignPassPanelFlag(true);

	}

	public void cancel() {
		setSystemPassPanelFlag(false);
		setSignPassPanelFlag(false);

	}

	public ArcUsers getUser() {
		return user;
	}

	public void setUser(ArcUsers user) {
		this.user = user;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HrsMasterFile getHrsMasterFile() {
		return hrsMasterFile;
	}

	public void setHrsMasterFile(HrsMasterFile hrsMasterFile) {
		this.hrsMasterFile = hrsMasterFile;
	}

	public boolean isEmpFlag() {
		return empFlag;
	}

	public void setEmpFlag(boolean empFlag) {
		this.empFlag = empFlag;
	}

	public HrsEmpHistorical getEmpHistoricalInfo() {
		return empHistoricalInfo;
	}

	public void setEmpHistoricalInfo(HrsEmpHistorical empHistoricalInfo) {
		this.empHistoricalInfo = empHistoricalInfo;
	}

	public StreamedContent getMyImage() {
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public boolean isSystemPassPanelFlag() {
		return systemPassPanelFlag;
	}

	public void setSystemPassPanelFlag(boolean systemPassPanelFlag) {
		this.systemPassPanelFlag = systemPassPanelFlag;
	}

	public boolean isSignPassPanelFlag() {
		return signPassPanelFlag;
	}

	public void setSignPassPanelFlag(boolean signPassPanelFlag) {
		this.signPassPanelFlag = signPassPanelFlag;
	}

	public String getSignPassOld() {
		return signPassOld;
	}

	public void setSignPassOld(String signPassOld) {
		this.signPassOld = signPassOld;
	}

	public String getHahsedSignPassOld() {
		return hahsedSignPassOld;
	}

	public void setHahsedSignPassOld(String hahsedSignPassOld) {
		this.hahsedSignPassOld = hahsedSignPassOld;
	}

	public String getHashedSystemPassOld() {
		return hashedSystemPassOld;
	}

	public void setHashedSystemPassOld(String hashedSystemPassOld) {
		this.hashedSystemPassOld = hashedSystemPassOld;
	}

	public WrkInboxFolder getFolderInfo() {
		return folderInfo;
	}

	public void setFolderInfo(WrkInboxFolder folderInfo) {
		this.folderInfo = folderInfo;
	}

	public StreamedContent getStreamImage() {
		return streamImage;
	}

	public void setStreamImage(StreamedContent streamImage) {
		this.streamImage = streamImage;
	}

	public String getSignNewPassword() {
		return signNewPassword;
	}

	public String getConfirmSignPass() {
		return confirmSignPass;
	}

	public void setConfirmSignPass(String confirmSignPass) {
		this.confirmSignPass = confirmSignPass;
	}

	public String getConfirmSystemPass() {
		return confirmSystemPass;
	}

	public void setConfirmSystemPass(String confirmSystemPass) {
		this.confirmSystemPass = confirmSystemPass;
	}

	public void setSignNewPassword(String signNewPassword) {
		this.signNewPassword = signNewPassword;
	}

	public String getSystemPassOld() {
		return systemPassOld;
	}

	public void setSystemPassOld(String systemPassOld) {
		this.systemPassOld = systemPassOld;
	}

	public String getSystemNewPassword() {
		return systemNewPassword;
	}

	public String getHashedNewSignPassword() {
		return hashedNewSignPassword;
	}

	public void setHashedNewSignPassword(String hashedNewSignPassword) {
		this.hashedNewSignPassword = hashedNewSignPassword;
	}

	public String getHashedNewSystemPassword() {
		return hashedNewSystemPassword;
	}

	public void setHashedNewSystemPassword(String hashedNewSystemPassword) {
		this.hashedNewSystemPassword = hashedNewSystemPassword;
	}

	public void setSystemNewPassword(String systemNewPassword) {
		this.systemNewPassword = systemNewPassword;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

}
