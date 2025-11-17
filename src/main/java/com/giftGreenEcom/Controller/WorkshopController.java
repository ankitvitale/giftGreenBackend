package com.giftGreenEcom.Controller;

import com.giftGreenEcom.DTO.WorkShopDto.WorkshopDTO;
import com.giftGreenEcom.Service.WorkShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workshop")
public class WorkshopController {

    @Autowired
    private WorkShopService workShopService;


    @PostMapping("/createWorkShop")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<WorkshopDTO> createWorkShop(@RequestBody WorkshopDTO workshopDTO){
        WorkshopDTO createWorkShop= workShopService.createWorkShop(workshopDTO);
        return ResponseEntity.ok(createWorkShop);
    }

    @GetMapping("/getAllWorkShop")
    public ResponseEntity<List<WorkshopDTO>>  getAllWorkshop(){
        List<WorkshopDTO> workshopDTO=workShopService.getAllWorkshop();
        return ResponseEntity.ok(workshopDTO);
    }
    @GetMapping("/getWorkShop/{id}")
    public ResponseEntity<WorkshopDTO> getWorkShopById(@PathVariable Long id){
        WorkshopDTO workshopDTO=workShopService.getWorkshopById(id);
        return ResponseEntity.ok(workshopDTO);
    }

    @PutMapping("/updateWorkShop/{id}")
    public ResponseEntity<WorkshopDTO> updateWorkShop(@PathVariable Long id, @RequestBody WorkshopDTO workshopDTO){
        WorkshopDTO updatedWorkshop = workShopService.updateWorkShop(id, workshopDTO);
        return ResponseEntity.ok(updatedWorkshop);
    }



    @DeleteMapping("/deleteWorkShop/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteWorkShop(@PathVariable Long id) {
        workShopService.deleteWorkShop(id);
        return ResponseEntity.ok("Workshop deleted successfully with ID: " + id);
    }
}
