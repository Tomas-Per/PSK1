package com.cars.cardealership.persistence;

import com.cars.cardealership.entities.Car;
import com.cars.cardealership.interceptors.LoggedInvocation;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ViewScoped
@Named
public class CarUpdater implements Serializable {

    private Car car;

    @Inject
    private CarsDAO carsDAO;

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long carId = Long.parseLong(requestParameters.get("carId"));
        this.car = carsDAO.findOne(carId);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @LoggedInvocation
    public String updateModel() {
        try{
            carsDAO.update(this.car);
        } catch (OptimisticLockException e) {
            return "/model.xhtml?faces-redirect=true&carId=" + this.car.getId() + "&error=optimistic-lock-exception";
        }
        return "index.xhtml";
    }
}
