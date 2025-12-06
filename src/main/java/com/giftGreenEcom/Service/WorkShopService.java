package com.giftGreenEcom.Service;

import com.giftGreenEcom.DTO.WorkShopDto.WorkshopBookingDTO;
import com.giftGreenEcom.DTO.WorkShopDto.WorkshopDTO;
import com.giftGreenEcom.Entity.User;
import com.giftGreenEcom.Entity.Workshop;
import com.giftGreenEcom.Entity.WorkshopBooking;
import com.giftGreenEcom.Repository.UserRepository;
import com.giftGreenEcom.Repository.WorkshopBookingRepository;
import com.giftGreenEcom.Repository.WorkshopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkShopService {
    @Autowired
    private WorkshopRepository workshopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkshopBookingRepository workshopBookingRepository;


    public WorkshopDTO createWorkShop(WorkshopDTO workshopDTO) {

        Workshop workshop=new Workshop();
        workshop.setNameOfWorkShop(workshopDTO.getNameOfWorkShop());
        workshop.setDate(workshopDTO.getDate());
        workshop.setTime(workshopDTO.getTime());
        workshop.setPrice(workshopDTO.getPrice());
        Workshop savedWorkshop = workshopRepository.save(workshop);

        return new WorkshopDTO(
                savedWorkshop.getId(),
                savedWorkshop.getNameOfWorkShop(),
                savedWorkshop.getDate(),
                savedWorkshop.getTime(),
                savedWorkshop.getPrice()
        );
    }

    public List<WorkshopDTO> getAllWorkshop() {
        List<Workshop> workshops=workshopRepository.findAll();
        return workshops.stream().map(workshop -> new WorkshopDTO(
                workshop.getId(),
                workshop.getNameOfWorkShop(),
                workshop.getDate(),
                workshop.getTime(),
                workshop.getPrice()
        )).collect(Collectors.toList());


    }

    public WorkshopDTO getWorkshopById(Long id) {
        Workshop workshop= workshopRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("WorkShop Id"+id+"is Not Found"));
        return new WorkshopDTO(
                workshop.getId(),
                workshop.getNameOfWorkShop(),
                workshop.getDate(),
                workshop.getTime(),
                workshop.getPrice()
        );
    }

    public WorkshopDTO updateWorkShop(Long id, WorkshopDTO workshopDTO) {
        Workshop workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work Shop id " + id + " is not found"));

        workshop.setNameOfWorkShop(workshopDTO.getNameOfWorkShop());
        workshop.setDate(workshopDTO.getDate());
        workshop.setTime(workshopDTO.getTime());
        workshop.setPrice(workshopDTO.getPrice());

        Workshop updated = workshopRepository.save(workshop);

        return new WorkshopDTO(
                updated.getId(),
                updated.getNameOfWorkShop(),
                updated.getDate(),
                updated.getTime(),
                updated.getPrice()
        );
    }

    public void deleteWorkShop(Long id) {
        Workshop workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workshop id " + id + " is not found"));

        workshopRepository.delete(workshop);
    }

    public WorkshopBooking bookWorkshop(Long workshopId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workshop workshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new RuntimeException("Workshop not found"));

        WorkshopBooking booking = new WorkshopBooking();
        booking.setUser(user);
        booking.setWorkshop(workshop);

        return workshopBookingRepository.save(booking);
    }

    public List<WorkshopBookingDTO> getAllBookings() {
        List<WorkshopBooking> bookings = workshopBookingRepository.findAll();
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private WorkshopBookingDTO convertToDTO(WorkshopBooking booking) {
        Workshop workshop = booking.getWorkshop();
        User user = booking.getUser();

        WorkshopBookingDTO dto = new WorkshopBookingDTO();
        dto.setBookingId(booking.getId());
     //   dto.setWorkshopId(workshop.getId());
        dto.setWorkshopName(workshop.getNameOfWorkShop());
        dto.setWorkshopDate(workshop.getDate());
        dto.setWorkshopTime(workshop.getTime());
        dto.setPrice(workshop.getPrice());

    //    dto.setUserId(user.getId());
        dto.setUserName(user.getName());
        dto.setUserEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        dto.setBookingDate(booking.getBookingDate());

        return dto;
    }


}
