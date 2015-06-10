/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.abk.backend;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * Other jsonProviders are.
 * 
 * Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature"); 
 * Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
 * 
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@ApplicationPath(ResourcePaths.APPLICATION_PATH)
public class AbkApplication extends Application {

    public AbkApplication() {
        BeanConfig beanConfig = new BeanConfig();
        
       
        
        beanConfig.setBasePath("abk-backend/" + ResourcePaths.APPLICATION_PATH);
        beanConfig.setContact("philippe@tjonahen.nl");
        beanConfig.setDescription("Rest api for the ABK frontend");
        beanConfig.setHost("localhost:8181");
        beanConfig.setLicense("gpl");
        beanConfig.setLicenseUrl("/license.html");
        beanConfig.setPrettyPrint(true);
        beanConfig.setResourcePackage("nl.tjonahen.abk.backend.boundry.account,"+
                "nl.tjonahen.abk.backend.boundry.admin,"+
                "nl.tjonahen.abk.backend.boundry.costcenter,"+
                "nl.tjonahen.abk.backend.boundry.transaction,"+
                "nl.tjonahen.abk.backend.boundry.upload");
        beanConfig.setScan(true);
        beanConfig.setSchemes(new String[]{"https"});
        beanConfig.setTermsOfServiceUrl("");
        beanConfig.setTitle("ABK rest services");
        beanConfig.setVersion("2.0.0");
        
    }

    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // following code can be used to customize Jersey 2.0 JSON provider:
        try {
            resources.add(Class.forName("org.glassfish.jersey.jackson.JacksonFeature"));
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically re-generated by NetBeans REST support to
     * populate given list with all resources defined in the project.
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(nl.tjonahen.abk.backend.AbkCrossOriginResourceSharingFilter.class);
        resources.add(nl.tjonahen.abk.backend.HttpHeaderLoggingFilter.class);
        resources.add(nl.tjonahen.abk.backend.boundry.account.AccountsResource.class);
        resources.add(nl.tjonahen.abk.backend.boundry.admin.AdminResource.class);
        resources.add(nl.tjonahen.abk.backend.boundry.costcenter.CostCentersResource.class);
        resources.add(nl.tjonahen.abk.backend.boundry.transaction.FinancialTransactionsResource.class);
    }
}
