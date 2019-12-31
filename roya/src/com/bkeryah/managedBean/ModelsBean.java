package com.bkeryah.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class ModelsBean {
	private String pageName;

	@PostConstruct
	public void modelType() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);

		Integer type = (Integer) httpSession.getAttribute("type");
		if (type == 236) {
			pageName = "../projects/extractView.xhtml";
		} else if (type == 237) {
			pageName = "../store/exchangeRequestView.xhtml";

		} else if (type == 238)
			pageName = "../store/actualyExchangView.xhtml";
		else if (type == 117)
			pageName = "../licences/health_certificate.xhtml";
		else if (type == 239)
			pageName = "../shared/procurement_materials_view.xhtml";
		else if (type == 241)
			pageName = "../pages/licences/fine_rebound_model_view.xhtml";
		else if (type == 28)
			pageName = "../pages/hr/sick_vacation_view.xhtml";
		else if (type == 197)
			pageName = "../store/memoReceiptView.xhtml";
		else if (type == 119)
			pageName = "../pages/licences/new_building_view.xhtml";
		else if (type == 243)
			pageName = "../pages/licences/attach_building_view.xhtml";
		else if (type == 244)
			pageName = "../pages/licences/new_building_wall_view.xhtml";
		else if (type == 246)
			pageName = "../hr/charterPerformanceView.xhtml";
		else if (type == 247)
			pageName = "../hr/charterPerformanceView.xhtml";
		else if (type == 248)
			pageName = "../hr/generalAppreciationView.xhtml";
		else if (type == 250)
			pageName = "../store/store_return_view.xhtml";
		else if (type == 251)
			pageName = "../store/store_temporary_receipt_view.xhtml";
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
