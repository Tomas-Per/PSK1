package com.cars.cardealership.persistence;

import com.cars.cardealership.interceptors.LoggedInvocation;
import com.cars.cardealership.services.ModelGenerator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class CarModelGen implements Serializable {

    @Inject
    private ModelGenerator modelGenerator;

    private CompletableFuture<String> modelGenTask = null;

    @LoggedInvocation
    public String generateModel(String make) {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        modelGenTask = CompletableFuture.supplyAsync(() -> modelGenerator.generateModel(make));

        return  "/model.xhtml?faces-redirect=true&carId=" + requestParameters.get("carId");
    }

    public boolean isGeneratorRunning() {
        return modelGenTask != null && !modelGenTask.isDone();
    }

    public String getStatus() throws ExecutionException, InterruptedException {
        if (modelGenTask == null) {
            return null;
        } else if (isGeneratorRunning()) {
            return "Generating";
        }
        return "Model: " + modelGenTask.get();
    }
}
