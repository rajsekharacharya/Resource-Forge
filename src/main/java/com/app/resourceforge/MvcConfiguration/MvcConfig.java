package com.app.resourceforge.MvcConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Value("${file.upload-dir.windows}")
	private String windowsUploadDir;

	@Value("${file.upload-dir.linux}")
	private String linuxUploadDir;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String[][] views = {
				// Common
				{ "/", "login" }, { "/login", "login" }, { "/logout", "login" },
				{ "/forgotpassword", "forgotpassword" }, { "/profile", "profile" },

				// Company Views
				{ "/home-company", "company/home" }, { "/super-company", "company/superCompany" },
				{ "/subscription", "company/subscription" }, { "/superadmin", "company/superadmin" },
				{ "/saasuser", "company/user" },

				// Super Admin Views
				{ "/home-super-admin", "SuperAdmin/home" }, { "/master-subscription", "SuperAdmin/subscription" },
				{ "/master-company", "SuperAdmin/company" }, { "/admin-management", "SuperAdmin/user" },
				{ "/company-settings", "SuperAdmin/settings" },

				// Admin Views
				{ "/home", "admin/home" }, { "/user-registration", "admin/userRegistration" },
				{ "/location", "admin/location" }, { "/asset-type", "admin/assetType" },
				{ "/department", "admin/department" }, { "/employees", "admin/employees" },
				{ "/manufacturer", "admin/manufacturer" }, { "/project-master", "admin/projectMaster" },
				{ "/supplier", "admin/supplier" }, { "/asset-category", "admin/assetCategory" },
				{ "/master-assets", "admin/masterAssets" }, { "/asset-management", "admin/assetManagement" },
				{ "/project-management", "admin/projectManagement" }, { "/asset-finance", "admin/assetFinance" },
				{ "/asset-purchase", "admin/assetPurchase" }, { "/asset-amc", "admin/assetAmc" },
				{ "/asset-insurance", "admin/assetInsurance" }, { "/report", "admin/report" },
				{ "/settings", "admin/settings" }, { "/calllog", "admin/calllog" },
				{ "/serviceBook", "admin/serviceEngineerLog" }, { "/logistics", "admin/logistics" },
				{ "/print", "admin/print" },

				// General Pages
				{ "/user-guide", "user-guide" }, { "/release-notes", "release-notes" },{ "/privacy-policy", "privacy-policy" },{ "/client", "client" },

				// Error Pages
				{ "/404", "error404" }, { "/403", "error403" }, { "/505", "error500" },
				{ "/error-subscription-end", "errorSubscriptionEnd" }
		};

		for (String[] view : views) {
			registry.addViewController(view[0]).setViewName(view[1]);
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String os = System.getProperty("os.name").toLowerCase();
		String uploadDir = os.contains("win") ? windowsUploadDir : linuxUploadDir;
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadDir + "/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginPageInterceptor());
	}
}
