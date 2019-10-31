package com.bkeryah.mails;

public class MailsFactory extends Factory {
	private static MailsFactory instance = null;

	private MailsFactory() {
	}

	public static synchronized MailsFactory getInstance() {
		if (instance == null) {
			instance = new MailsFactory();
		}
		return instance;
	}

	public MailExecutor<?> getExecutor(final MailTypeEnum mailType) {

		switch (mailType) {
		case AUTORIZATION:
			return new AutorisationExecutor(mailType);
		case SPECIAL_AUTORIZATION:
			return new SpecialAutorisationExecutor(mailType);
		case LEAVESUCCESSOR:
			return new LeaveSuccessorExecutor(mailType);
		case VACATION:
			return new VacationExecutor(MailTypeEnum.VACATION);
		case VACNEEDED:
			return new VacationExecutor(MailTypeEnum.VACNEEDED);
		case SALARY:
			return new HrLetterExcuter(MailTypeEnum.SALARY);
		case SALAR:
			return new HrLetterExcuter(MailTypeEnum.SALAR);
		case WORKMISSION:
			return new MissionExecutor(mailType);
		case LIC:
			return new LicExecutor(mailType);
		case MEDICALREPORT:
			return new MedicalReportRequestExcuter(MailTypeEnum.MEDICALREPORT);
		case TRAINING:
			return new TrainingExecutor(mailType);
		case COPY:
			return new TransactionExecutor(mailType);
		case MODAKARA:
			return new TransactionExecutor(mailType);
		case KITAB:
			return new TransactionExecutor(mailType);
		case PERSONAL_COMMENT:
			return new TransactionExecutor(mailType);
		default:
			return null;
		}
	}

	public String getPage(final MailTypeEnum mailType) {

		return "Models";
	}
}
