package application;

import domain.model.Diary;
import domain.model.Watering;
import domain.repository.DiaryRepository;
import dto.AccountDTO;
import dto.ModelMapper;
import dto.DiaryDTO;
import dto.WateringDTO;
import infra.database.option.account.TokenOption;
import infra.database.option.diary.PetPlantPKOption;
import infra.database.option.diary.UserPKOption;
import infra.database.option.watering.MonthDateOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiaryAppService {

    private DiaryRepository diaryRepo;

    public DiaryAppService(DiaryRepository postRepo){
        this.diaryRepo = postRepo;
    }

    public DiaryDTO createDiary(DiaryDTO diaryDTO){
        Diary diary = Diary.builder()
                            .petPlantPK(diaryDTO.getPetPlantPK())
                            .userPK(diaryDTO.getUserPK())
                            .title(diaryDTO.getTitle())
                            .content(diaryDTO.getContent())
                            .date(diaryDTO.getDate())
                            .diaryImg(diaryDTO.getDiaryImg())
                            .build();


        long postPK = diaryRepo.save(diary);

        diaryDTO.setPk(postPK);

        if(postPK>0){
            return diaryDTO;
        }else{
            return null;
        }
    }

    public void delete(DiaryDTO diaryDTO){
        Diary diary = diaryRepo.findByID(diaryDTO.getPk());
        diaryRepo.remove(diary);
    }

    public List<DiaryDTO> retrieveAll(AccountDTO accDTO){
        List<Diary> diaries = diaryRepo.findByOption(new UserPKOption(accDTO.getPk()));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public List<DiaryDTO> retrieve(long petPK){
        List<Diary> diaries = diaryRepo.findByOption(new infra.database.option.petPlant.PKOption(petPK));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public List<DiaryDTO> retrieveByMonthAndPetPK(DiaryDTO diaryDTO){
        List<Diary> diaryList = diaryRepo.findByOption(
                new infra.database.option.diary.UserPKOption(diaryDTO.getUserPK()),
                new PetPlantPKOption(diaryDTO.getPetPlantPK()),
                new infra.database.option.diary.MonthDateOption(diaryDTO.getDate())
        );
        List<DiaryDTO> resList = new ArrayList<>();

        for(Diary diary : diaryList){
            resList.add(
                    ModelMapper.<Diary, DiaryDTO>modelToDTO(diary, DiaryDTO.class)
            );
        }

        return resList;
    }

    public DiaryDTO update(DiaryDTO diaryDTO){
        Diary diary = diaryRepo.findByID(diaryDTO.getPk());

        diary.setTitle(diaryDTO.getTitle());
        diary.setContent(diaryDTO.getContent());
        diary.setDiaryImg(diaryDTO.getDiaryImg());
        diary.setPetPlantPK(diaryDTO.getPetPlantPK());
        diary.setDate(diaryDTO.getDate());

        diaryRepo.save(diary);

        return ModelMapper.modelToDTO(diary, DiaryDTO.class);
    }

}
