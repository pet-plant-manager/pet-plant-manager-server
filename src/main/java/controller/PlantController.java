package controller;

import application.PlantAppService;
import domain.repository.PlantRepository;
import dto.PlantDTO;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class PlantController {
    private final PlantAppService plantAppService;

    public PlantController(PlantRepository plantRepo) {
        this.plantAppService = new PlantAppService(plantRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "all":{
                return processAll(req);
            }
            case "recommend":{
                return processRecommend(req);
            }
            case "one":{
                return processOne(req);
            }
        }

        return null;
    }

    private Response processAll(Request req) {
        Response res = null;

        switch (req.method){
            case GET:{
                List<PlantDTO> data = plantAppService.retrieveAll();

                if(data==null){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("plantList", data);
            }
        }

        return res;
    }

    private Response processRecommend(Request req) {
        Response res = null;

        switch (req.method){
            case GET:{
                List<PlantDTO> data = plantAppService.recommendPlant((PlantDTO) req.data.get("plantDTO"));

                if(data.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("plantDTOList", data);
            }
        }

        return res;
    }

    private Response processOne(Request req) {
        Response res = null;

        switch (req.method){
            case GET :{
                PlantDTO dto = plantAppService.retrieveByID((PlantDTO) req.data.get("plantDTO"));

                if(dto==null){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("plantDTO", dto);
                }
                break;
            }
        }

        return res;
    }

}
