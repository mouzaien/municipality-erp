package utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.bkeryah.entities.PayBillDetails;

public class CalcFeesUtil {

	// A,B,D,E,F
	public static final char MClass = 'C';
	public static final double PREVIEW_FEES = 20;

	/*
	 * LicenseType 1 إنشاء 2 ترميم 3 هدم 4 اتمام البناء
	 */
	/*
	 * issueType 0 جديد 1 تجديد
	 */
	/*
	 * buildingType 1 سكني 2 سكني تجاري 3 تجاري
	 * 
	 */
	public static List<PayBillDetails> calculateBuildingFees(int LicenseType, int issueType, int buildingType,
			double buildingArea, double wallLength) {
		double sum = 0.0;
		double buildingFees = 0.0;
		double wallFees = 0.0;

		if (LicenseType == 0 || issueType == 0) {
			MsgEntry.addErrorMessage("نوع الترخيص  / الإصدار مطلوب");
			return null;
		}
		if (LicenseType != 8 &&  buildingType == 0) {
			MsgEntry.addErrorMessage("مساحة المبني  / نوع المبني مطلوبة");
			return null;
		}
		if (LicenseType == 8 && wallLength == 0) {
			MsgEntry.addErrorMessage("طول السور مطلوب  ");
			return null;
		}
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(1422106, 1422106, 100.0, "رسوم"));
			return result;
		}
		switch (LicenseType) {
		case 1:
			switch (buildingType) {
			case 1: // سكني

				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 3;
					wallFees = wallLength * 3;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 2.4;
					wallFees = wallLength * 2.4;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 1.8;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * .6;
					wallFees = wallLength * .6;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 2: // سكني تجاري

				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 4;
					wallFees = wallLength * 3;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 3.2;
					wallFees = wallLength * 2.4;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 2.4;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 1.6;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * .8;
					wallFees = wallLength * .6;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 3: // تجاري
				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 6;
					wallFees = wallLength * 3;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 4.8;
					wallFees = wallLength * 2.4;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 3.6;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 2.4;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * .6;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;

		case 2:

			switch (buildingType) {
			case 1: // سكني
				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 1.5;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 0.9;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 0.6;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.3;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 2: // سكني تجاري

				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 2;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 1.6;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 0.8;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.4;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 3: // تجاري
				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 3;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 2.4;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 1.8;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.6;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

			break;
		case 3:

			switch (buildingType) {
			case 1: // سكني
				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 1.5;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 0.9;
					wallFees = wallLength * .6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 0.6;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.3;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 2: // سكني تجاري

				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 2;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 1.6;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 0.8;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.4;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			case 3: // تجاري
				switch (MClass) {
				case 'A':
					buildingFees = buildingArea * 3;
					wallFees = wallLength * 1.5;
					sum = buildingFees + wallFees;
					break;
				case 'B':
					buildingFees = buildingArea * 2.4;
					wallFees = wallLength * 1.2;
					sum = buildingFees + wallFees;
					break;
				case 'C':
					buildingFees = buildingArea * 1.8;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'D':
					buildingFees = buildingArea * 1.2;
					wallFees = wallLength * 0.6;
					sum = buildingFees + wallFees;
					break;
				case 'E':
					buildingFees = buildingArea * 0.6;
					wallFees = wallLength * 0.3;
					sum = buildingFees + wallFees;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

			break;
		case 4:
			switch (buildingType) {
			case 1: // سكني
				sum = 200;
				break;
			case 2: // سكني تجاري
				sum = 300;
				break;
			case 3: // تجاري
				sum = 400;
				break;
			default:
				break;
			}
			break;
		case 5:
			sum = buildingArea * 0.4;
			break;
		case 6:
			sum = 10;
			break;
		case 7:
			switch (buildingType) {
			case 1:
				sum = buildingArea * 0.4;
				break;
			case 3:
				sum = buildingArea * 0.8;
				break;
			default:
				break;
			}
			break;
		case 8:
			switch (MClass) {
			case 'A':

				wallFees = wallLength * 1.5;
				sum = buildingFees + wallFees;
				break;
			case 'B':
				wallFees = wallLength * 1.2;
				sum = buildingFees + wallFees;
				break;
			case 'C':
				wallFees = wallLength * 1.2;
				sum = buildingFees + wallFees;
				break;
			case 'D':
				wallFees = wallLength * 1.2;
				sum = buildingFees + wallFees;
				break;
			case 'E':
				wallFees = wallLength * 0.3;
				sum = buildingFees + wallFees;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		result.add(new PayBillDetails(1422106, 1422106, roundNum(sum), "رسوم"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));
		return result;
	}

	public static List<PayBillDetails> calculateTradeingMarketFees(int issueType, double advArea, double addAdvArea,
			double marketArea, int activityType, int issueYears) {
		List<PayBillDetails> result = new ArrayList<>();
		double sum = 0.0;
		double advcost = 0.0;
		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}

		if ((issueType == 1 || issueType == 2)
				&& (marketArea == 0 || advArea == 0 || issueYears == 0 || activityType == 0)) {
			MsgEntry.addErrorMessage("أدخل بيانات  النشاط  / مساحة اللوحة / المحل  / سنوات الإصدار ");
			return null;
		}

		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم "));
			return result;
		}
		if (issueType == 4 || issueType == 5 || issueType == 6) {
			result.add(new PayBillDetails(1422103, 1422103, 20.0, "رسوم "));
			result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));
			return result;
		}

		switch (MClass) {
		case 'A':

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 'B':

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 'C':

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 'D':

			advcost = (advArea * 150) + (addAdvArea * 250);
			break;
		case 'E':

			advcost = (advArea * 125) + (addAdvArea * 250);
			break;
		default:
			break;
		}

		int marketGrade = findGradeByArea(marketArea);
		switch (activityType) {
		case 1: // trade market
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 6;

					break;
				case 'B':
					sum = marketArea * 4.8;
					break;
				case 'C':
					sum = marketArea * 3.6;

					break;
				case 'D':
					sum = marketArea * 2.4;

					break;
				case 'E':
					sum = marketArea * 1.2;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.6;

					break;
				case 'B':
					sum = marketArea * 0.48;
					break;
				case 'C':
					sum = marketArea * 0.36;

					break;
				case 'D':
					sum = marketArea * 0.24;

					break;
				case 'E':
					sum = marketArea * 0.12;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;
					break;
				default:
					break;
				}
				break;

			default:
				break;
			}

			break;
		case 2: // Oil changing and car washing
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 6;

					break;
				case 'B':
					sum = marketArea * 4.8;
					break;
				case 'C':
					sum = marketArea * 3.6;

					break;
				case 'D':
					sum = marketArea * 2.4;

					break;
				case 'E':
					sum = marketArea * 1.2;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.6;

					break;
				case 'B':
					sum = marketArea * 0.48;
					break;
				case 'C':
					sum = marketArea * 0.36;

					break;
				case 'D':
					sum = marketArea * 0.24;

					break;
				case 'E':
					sum = marketArea * 0.12;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 3: // labs and workshops
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 6;

					break;
				case 'B':
					sum = marketArea * 4.8;
					break;
				case 'C':
					sum = marketArea * 3.6;

					break;
				case 'D':
					sum = marketArea * 2.4;

					break;
				case 'E':
					sum = marketArea * 1.2;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.6;

					break;
				case 'B':
					sum = marketArea * 0.48;
					break;
				case 'C':
					sum = marketArea * 0.36;

					break;
				case 'D':
					sum = marketArea * 0.24;

					break;
				case 'E':
					sum = marketArea * 0.12;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 4: // wedding palace
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;

		case 5:
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 6:
			if (marketArea > 1 && marketArea <= 5000) {
				switch (MClass) {
				case 'A':
					sum = marketArea * 8;

					break;
				case 'B':
					sum = marketArea * 6.4;
					break;
				case 'C':
					sum = marketArea * 4.8;

					break;
				case 'D':
					sum = marketArea * 3.2;

					break;
				case 'E':
					sum = marketArea * 1.6;
					break;
				default:
					break;
				}
			}
			if (marketArea > 5000) {
				switch (MClass) {
				case 'A':
					sum = marketArea * 4;

					break;
				case 'B':
					sum = marketArea * 3.2;
					break;
				case 'C':
					sum = marketArea * 2.4;

					break;
				case 'D':
					sum = marketArea * 1.6;

					break;
				case 'E':
					sum = marketArea * 0.8;
					break;
				default:
					break;
				}
			}
			break;
		case 7: // fun places and etertainment
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 8: // medical
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 9: // educational
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 10: // stores
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1;

					break;
				case 'B':
					sum = marketArea * 0.8;
					break;
				case 'C':
					sum = marketArea * 0.6;

					break;
				case 'D':
					sum = marketArea * 0.4;

					break;
				case 'E':
					sum = marketArea * 0.2;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.5;

					break;
				case 'B':
					sum = marketArea * 0.4;
					break;
				case 'C':
					sum = marketArea * 0.3;

					break;
				case 'D':
					sum = marketArea * 0.2;

					break;
				case 'E':
					sum = marketArea * 0.1;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.25;

					break;
				case 'B':
					sum = marketArea * 0.20;
					break;
				case 'C':
					sum = marketArea * 0.15;

					break;
				case 'D':
					sum = marketArea * 0.10;

					break;
				case 'E':
					sum = marketArea * 0.05;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.10;

					break;
				case 'B':
					sum = marketArea * 0.08;
					break;
				case 'C':
					sum = marketArea * 0.06;

					break;
				case 'D':
					sum = marketArea * 0.04;

					break;
				case 'E':
					sum = marketArea * 0.02;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.05;

					break;
				case 'B':
					sum = marketArea * 0.04;
					break;
				case 'C':
					sum = marketArea * 0.03;

					break;
				case 'D':
					sum = marketArea * 0.02;

					break;
				case 'E':
					sum = marketArea * 0.01;
					break;
				default:
					break;
				}
				break;
			}
			break;
		case 11: // others
			switch (marketGrade) {
			case 1:
				switch (MClass) {
				case 'A':
					sum = marketArea * 3;

					break;
				case 'B':
					sum = marketArea * 2.4;
					break;
				case 'C':
					sum = marketArea * 1.8;

					break;
				case 'D':
					sum = marketArea * 1.2;

					break;
				case 'E':
					sum = marketArea * 0.6;

					break;
				default:
					break;
				}
				break;
			case 2:
				switch (MClass) {
				case 'A':
					sum = marketArea * 1.5;

					break;
				case 'B':
					sum = marketArea * 1.2;
					break;
				case 'C':
					sum = marketArea * 0.9;

					break;
				case 'D':
					sum = marketArea * 0.6;

					break;
				case 'E':
					sum = marketArea * 0.3;

					break;
				default:
					break;
				}
				break;
			case 3:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.75;

					break;
				case 'B':
					sum = marketArea * 0.6;
					break;
				case 'C':
					sum = marketArea * 0.45;

					break;
				case 'D':
					sum = marketArea * 0.3;

					break;
				case 'E':
					sum = marketArea * 0.15;

					break;
				default:
					break;
				}
				break;
			case 4:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.3;

					break;
				case 'B':
					sum = marketArea * 0.24;
					break;
				case 'C':
					sum = marketArea * 0.18;

					break;
				case 'D':
					sum = marketArea * 0.12;

					break;
				case 'E':
					sum = marketArea * 0.06;

					break;
				default:
					break;
				}
				break;
			case 5:
				switch (MClass) {
				case 'A':
					sum = marketArea * 0.15;

					break;
				case 'B':
					sum = marketArea * 0.12;
					break;
				case 'C':
					sum = marketArea * 0.09;

					break;
				case 'D':
					sum = marketArea * 0.06;

					break;
				case 'E':
					sum = marketArea * 0.03;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

		default:
			break;
		}

		result.add(new PayBillDetails(1422103, 1422103, roundNum(sum * issueYears), "رسوم "));
		result.add(new PayBillDetails(11452512, 11452512, roundNum(advcost * issueYears), "لوحات"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));

		return result;
	}

	public static List<PayBillDetails> calculateBuildingShelterFees(int issueType, double advArea, double addAdvArea,
			int unitsCount, int issueYears, int Tradeclass) {
		double sum = 0.0;
		double advcost = 0.0;
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم"));
			return result;
		}
		if (issueType == 0 || Tradeclass == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار  - الفئة السكنية    ");
			return null;
		}
		if ((issueType == 1 || issueType == 2) && (unitsCount == 0 || advArea == 0 || issueYears == 0)) {
			MsgEntry.addErrorMessage("أدخل عدد الوحدات - مساحة اللوحة -سنوات الإصدار  ");
			return null;
		}

		switch (Tradeclass) {
		case 1:
			switch (MClass) {
			case 'A':
				sum = unitsCount * 250;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = unitsCount * 200;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = unitsCount * 150;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = unitsCount * 100;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = unitsCount * 50;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}
			break;
		case 2:
			switch (MClass) {
			case 'A':
				sum = unitsCount * 200;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = unitsCount * 160;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = unitsCount * 120;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = unitsCount * 80;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = unitsCount * 40;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}
			break;

		case 3:

			switch (MClass) {
			case 'A':
				sum = unitsCount * 150;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = unitsCount * 120;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = unitsCount * 90;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = unitsCount * 60;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = unitsCount * 30;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}
			break;

		case 4:

			switch (MClass) {
			case 'A':
				sum = unitsCount * 100;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = unitsCount * 80;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = unitsCount * 60;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = unitsCount * 40;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = unitsCount * 20;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}

			break;

		case 5:
			switch (MClass) {
			case 'A':
				sum = unitsCount * 50;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = unitsCount * 40;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = unitsCount * 30;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = unitsCount * 20;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = unitsCount * 10;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

		result.add(new PayBillDetails(1422103, 1422103, roundNum(sum), "رسوم"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));

		result.add(new PayBillDetails(11452512, 11452512, advcost * issueYears, "لوحات"));

		return result;
	}

	public static List<PayBillDetails> calculatePetrolStationFees(int issueType, double advArea, double addAdvArea,
			int issueYears, double area, boolean isOutOfUrbanScale) {
		double sum = 0.0;
		double advcost = 0.0;
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم"));
			return result;
		}

		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}
		if ((issueType == 2) && (advArea == 0 || issueYears == 0 || area == 0)) {
			MsgEntry.addErrorMessage("أدخل مساحة المحطة - سنوات الإصدار - مساحة اللوحة");
			return null;
		}

		switch (issueType) {
		case 1:
			result.add(new PayBillDetails(1422106, 1422106, 3000.0, "رسوم"));
			// result.add(new PayBillDetails(11452512, 11452512,
			// roundNum(advcost * issueYears), "لوحات"));
			result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));
			break;
		case 2:
			result = calculateTradeingMarketFees(issueType, advArea, addAdvArea, area, 1, issueYears);
			break;
		case 3:
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم"));
			break;
		default:
			break;
		}
		return result;

	}

	public static List<PayBillDetails> calculateMobileCartsFees(int issueType, double addAdvArea, int issueYears) {
		double sum = 0.0;
		double advcost = 0.0;
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم"));
			return result;
		}
		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}
		if ((issueType == 1 || issueType == 2) && (issueYears == 0)) {
			MsgEntry.addErrorMessage("أدخل سنوات الترخيص");
			return null;

		}

		sum = 200 * issueYears;

		switch (MClass) {
		case 'A':
			advcost = (addAdvArea * 300);
			break;
		case 'B':
			advcost = (addAdvArea * 300);
			break;
		case 'C':
			advcost = (addAdvArea * 300);
			break;
		case 'D':
			advcost = (addAdvArea * 250);
			break;
		case 'E':
			advcost = (addAdvArea * 250);
			break;
		default:
			break;
		}

		advcost = (addAdvArea * 300);

		result.add(new PayBillDetails(1422103, 1422103, roundNum(sum), "رسوم"));
		result.add(new PayBillDetails(11452512, 11452512, roundNum(advcost * issueYears), "لوحات"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));

		return result;

	}

	public static List<PayBillDetails> calculateATMFees(int issueType, double advArea, double addAdvArea,
			int issueYears) {
		double sum = 0.0;
		double advcost = 0.0;
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "رسوم"));
			return result;
		}

		if (issueType == 1) {
			result.add(new PayBillDetails(1422106, 1422106, 3000.0, "رسوم"));
			result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));
			return result;
		}

		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}
		//
		if ((issueType == 2) && (advArea == 0 || issueYears == 0)) {
			MsgEntry.addErrorMessage("أدخل    سنوات الإصدار - مساحة اللوحة");
			return null;
		}

		switch (issueType) {
		case 1: // جديد
			switch (MClass) {
			case 'A':
				sum = 5000;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = 4000;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = 3000;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = 2000;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = 1000;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}

			break;

		case 2: // تجديد
			switch (MClass) {
			case 'A':
				sum = 1000;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'B':
				sum = 800;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'C':
				sum = 600;
				advcost = (advArea * 150) + (addAdvArea * 300);
				break;
			case 'D':
				sum = 400;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			case 'E':
				sum = 200;
				advcost = (advArea * 125) + (addAdvArea * 250);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		result.add(new PayBillDetails(1422103, 1422103, roundNum(sum * issueYears), "رسوم"));
		result.add(new PayBillDetails(11452512, 11452512, roundNum(advcost * issueYears), "لوحات"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));

		return result;
	}

	public static List<PayBillDetails> calculateTelecomTowerFees(int issueType, int issueYears) {
		double sum = 0.0;
		List<PayBillDetails> result = new ArrayList<>();
		if (issueType == 3) {
			result.add(new PayBillDetails(11100, 11102, 100.0, "رسوم"));
			return result;
		}
		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}
		if ((issueType == 1 || issueType == 2) && issueYears == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار - سنوات الإصدار    ");
			return null;
		}

		switch (issueType) {
		case 1: // جديد
			switch (MClass) {
			case 'A':
				sum = 5000;
				break;
			case 'B':
				sum = 4000;
				break;
			case 'C':
				sum = 3000;
				break;
			case 'D':
				sum = 2000;
				break;
			case 'E':
				sum = 1000;
				break;
			default:
				break;
			}

			break;

		case 2: // تجديد
			switch (MClass) {
			case 'A':
				sum = 500 * issueYears;
				break;
			case 'B':
				sum = 400 * issueYears;
				break;
			case 'C':
				sum = 300 * issueYears;
				break;
			case 'D':
				sum = 200 * issueYears;
				break;
			case 'E':
				sum = 100 * issueYears;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		result.add(new PayBillDetails(11100, 11102, roundNum(sum), "رسوم"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "كشفية"));

		return result;
	}

	public static List<PayBillDetails> calculateHealthCertificate(int issueType) {
		List<PayBillDetails> result = new ArrayList<>();

		if (issueType == 3) {
			result.add(new PayBillDetails(1422108, 1422108, 100.0, "رسوم"));
			return result;
		}
		if (issueType == 0) {
			MsgEntry.addErrorMessage("أختر نوع الإصدار   ");
			return null;
		}

		result.add(new PayBillDetails(1422108, 1422108, 60.0, "رسوم"));
		return result;
	}

	public static int findGradeByArea(double marketArea) {
		int result = 0;
		if (marketArea <= 5000) {
			result = 1;
		}
		if (marketArea > 5000 && marketArea <= 10000) {
			result = 2;
		}
		if (marketArea > 10000 && marketArea <= 20000) {
			result = 3;
		}
		if (marketArea > 20000 && marketArea <= 30000) {
			result = 4;
		}
		if (marketArea > 30000) {
			result = 5;
		}
		return result;

	};

	public static double roundNum(double num) {
		BigDecimal bd = new BigDecimal(num).setScale(2, RoundingMode.HALF_EVEN);
		num = bd.doubleValue();
		// return Math.round((num * 100.0) / 100.0d);
		return num;
	}
}
