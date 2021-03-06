package controller;

import application.NoticeAppService;
import domain.repository.*;
import dto.AccountDTO;
import dto.MessageDTO;
import dto.NoticeDTO;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class NoticeController {

    private final NoticeAppService noticeAppService;

    public NoticeController(AccountRepository accRepository, PetPlantRepository petPlantRepo, PlantRepository plantRepo, NoticeRepository noticeRepo, WateringRepository wateringRepo) {
        noticeAppService = new NoticeAppService(accRepository, petPlantRepo, plantRepo, noticeRepo, wateringRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "mynotices":{
                return processNotice(req);
            }
            case "delete":{
                return processDelete(req);
            }

        }

        return null;
    }

    private Response processNotice(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                String errorMsg = null;
                List<NoticeDTO> noticeDTOList = null;

                try{
                    noticeDTOList = noticeAppService.createNotice((AccountDTO) req.data.get("accountDTO"));
                }catch(IllegalArgumentException e){
                    errorMsg = e.getMessage();
                }finally {
                    if(noticeDTOList==null){
                        res = new Response(Response.StatusCode.FAIL);
                        res.data.put("messageDTO", new MessageDTO(errorMsg));
                    }else{
                        res = new Response(Response.StatusCode.SUCCESS);
                    }

                }

                res.data.put("noticeDTOList", noticeDTOList);
                break;
            }

            case GET:{
                List<NoticeDTO> resData = null;

                resData = noticeAppService.retrieveNotices(req.token);

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("noticeDTOList", resData);
                break;
            }
        }

        return res;
    }


    private Response processDelete(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                try{
                    noticeAppService.deleteNotice();
                    res = new Response(Response.StatusCode.SUCCESS);
                }catch(IllegalArgumentException e){
                    MessageDTO errorMsg = new MessageDTO(e.getMessage());
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", errorMsg);
                }
                break;
            }
        }

        return res;
    }
}
