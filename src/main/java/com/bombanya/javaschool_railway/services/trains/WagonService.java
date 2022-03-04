package com.bombanya.javaschool_railway.services.trains;

import com.bombanya.javaschool_railway.dao.trains.WagonDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.trains.Wagon;
import com.bombanya.javaschool_railway.entities.trains.WagonType;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WagonService {

    private final WagonDAO dao;
    private final WagonTypeService wagonTypeService;

    @Transactional
    public ServiceAnswer<List<Wagon>> saveNewWagons(int wagonTypeId, int wagonsToCreate){
        ServiceAnswer<WagonType> wagonType = wagonTypeService.getById(wagonTypeId);
        if (!wagonType.isSuccess()) return ServiceAnswerHelper.badRequest(wagonType.getErrorMessage());
        List<Wagon> newWagons = new ArrayList<>();
        for (int i = 0; i < wagonsToCreate; i++){
            Wagon newWagon = new Wagon();
            newWagon.setWagonType(wagonType.getServiceResult());
            dao.save(newWagon);
            newWagons.add(newWagon);
        }
        return ServiceAnswerHelper.ok(newWagons);
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Wagon>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Wagon> getById(int id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Wagon>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such wagon")
                        .build());
    }
}
