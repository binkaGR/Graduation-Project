package com.example.parkingserver;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/parkingspot")
public class ParkingSpotController {
    @Autowired
    private Parking_spotRepository parking_spotRepository;

    @Autowired
    private User_InformationRepository user_InformationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyReporsitory companyReporsitory;

    @Autowired
    private ParkingSpotReservationReprository parkingSpotReservationReprocsitory;

    private List<ParkingSpots> parkingSpotList;

    @GetMapping(path = "/AllParkingSpot/username={username}&password={password}")
    public @ResponseBody List<ParkingSpots> getAllParkingSpot(@PathVariable String username,
            @PathVariable String password) {
        UserJson userJson = new UserJson();
        parkingSpotList = new ArrayList<>();
        for (int i = 0; i < userRepository.count(); i++) {
            if (userRepository.findAll().get(i).GetUsername().equals(username) == true &&
                    userRepository.findAll().get(i).GetPassword().equals(password) == true) {
                userJson.Id = userRepository.findAll().get(i).GetId();
                userJson.Username = userRepository.findAll().get(i).GetUsername();
                userJson.Password = userRepository.findAll().get(i).GetPassword();
                userJson.Access = userRepository.findAll().get(i).GetAccess();
                break;
            }
        }
        if (userJson.Access != null) {
            int CompanyId = 0;
            for (int i = 0; i < user_InformationRepository.count(); i++) {
                if (user_InformationRepository.findById(userJson.Id.intValue()).get().GetUserId() == userJson.Id) {
                    CompanyId = user_InformationRepository.findById(userJson.Id.intValue()).get().GetCompanyId();
                    break;
                }
                if (CompanyId == 0) {
                    parkingSpotList = null;
                }
            }
            if (CompanyId != 0) {
                ParkingSpots parkingSpots = new ParkingSpots();
                for (int i = 0; i < parking_spotRepository.count(); i++) {
                    if (parking_spotRepository.findAll().get(i).GetCompanyId() == CompanyId) {
                        parkingSpots.IdParkingSpot = parking_spotRepository.findAll().get(i).GetIdParkingSpot();
                        parkingSpots.ParkingSpotName = parking_spotRepository.findAll().get(i)
                                .GetParkingSpotName();
                        parkingSpots.StatusParkingSpot = parking_spotRepository.findAll().get(i)
                                .GetStatusParkingSpot();
                        parkingSpotList.add(parkingSpots);
                        parkingSpots = new ParkingSpots();
                    }
                }
            } else {
                parkingSpotList = null;
            }
        } else {
            parkingSpotList = null;
        }
        return parkingSpotList;
    }

    @GetMapping(path = "/AllCompany")
    public @ResponseBody List<CompanysJson> GetCompanyInformation() {
        List<CompanysJson> companysJson = new ArrayList();
        CompanysJson getCompay = new CompanysJson();
        for (int i = 0; i < companyReporsitory.count(); i++) {
            getCompay.IdCompany = companyReporsitory.findAll().get(i).GetIdCompany().intValue();
            getCompay.company_name = companyReporsitory.findAll().get(i).GetCompanyName();
            getCompay.Address = companyReporsitory.findAll().get(i).GetAddress();
            companysJson.add(i, getCompay);
            getCompay = new CompanysJson();
        }
        return companysJson;
    }

    @PutMapping(path = "/GetParkingSpot/{id}/{StartTime}/{EndTime}/{CarNumber}")
    public @ResponseBody String getParkingSpot(@PathVariable Long id, @PathVariable Time StartTime,
            @PathVariable Time EndTime, @PathVariable String CarNumber) {
        String statusReservation = null;
        parkingSpotList = new ArrayList<>();
        ParkingSpots parkingSpot = new ParkingSpots();
        boolean makeReservation = false;
        for (int i = 0; i < parking_spotRepository.count(); i++) {
            if (parking_spotRepository.findAll().get(i).GetCompanyId() == id) {
                for (int j = 0; j < parkingSpotReservationReprocsitory.count(); j++) {
                    if (parkingSpotReservationReprocsitory.findAll().get(i).GetStartReservation()
                            .after(StartTime) == false ||
                            parkingSpotReservationReprocsitory.findAll().get(j).GetEndReservation()
                                    .before(EndTime) == false) {

                        ParkingSpotReservation parkingreservetion = new ParkingSpotReservation();
                        parkingreservetion.SetParkingSpotId(
                                parking_spotRepository.findAll().get(i).GetIdParkingSpot().intValue());
                        parkingreservetion.SetStartReservation(StartTime);
                        parkingreservetion.SetEndReservation(EndTime);
                        parkingreservetion.SetCarNumber(CarNumber);
                        parkingSpotReservationReprocsitory.save(parkingreservetion);
                        makeReservation = true;
                        break;
                    }
                }
            }
            if (makeReservation == true) {
                statusReservation = "Reservetion is create!";
                break;
            } else {
                statusReservation = "Reservetion is not create!";
            }
        }
        return statusReservation;
    }

    @GetMapping(path = "/GetParkingSpotInformation/{CarNumber}")
    public @ResponseBody ParkingReservationInformationJson getParkingSpotInformation(@PathVariable String CarNumber) {
        ParkingReservationInformationJson parkingReservationInformation = new ParkingReservationInformationJson();
        for (int i = 0; i < parkingSpotReservationReprocsitory.count(); i++) {

            if (parkingSpotReservationReprocsitory.findAll().get(i).GetCarNumber().equals(CarNumber) == true) {
                int spotId = parkingSpotReservationReprocsitory.findAll().get(i).GetParkingSpotId();
                int companyId = parking_spotRepository.findById(spotId).get().GetCompanyId();
                parkingReservationInformation.ParkingSpotName = parking_spotRepository.findById(spotId).get()
                        .GetParkingSpotName();
                parkingReservationInformation.Location = companyReporsitory.findById(companyId).get()
                        .GetCompanyName() +
                        "  " + companyReporsitory.findById(companyId).get().GetAddress();
                parkingReservationInformation.StartReservation = parkingSpotReservationReprocsitory.findAll().get(i)
                        .GetStartReservation();
                parkingReservationInformation.EndtReservation = parkingSpotReservationReprocsitory.findAll().get(i)
                        .GetEndReservation();
                break;
            }
        }
        return parkingReservationInformation;
    }

    @DeleteMapping(path = "/DeleteParkingSpot/{id}")
    public @ResponseBody String getParkingSpotInformation(@PathVariable int id) {
        String DeleteStatus = null;
        boolean flag = false;
        if (parking_spotRepository.findById(id).isPresent() == true) {
            for (int i = 0; i < parkingSpotReservationReprocsitory.count(); i++) {
                if (parkingSpotReservationReprocsitory.findAll().get(i).GetParkingSpotId() == id) {
                    flag = true;
                    DeleteStatus = "Availablel reservation!";
                    break;
                }
            }
            if (flag == false) {
                parking_spotRepository.deleteById(id);
                DeleteStatus = "Parking spot in remove!";
            } else {
                DeleteStatus = "Availablel reservation!";
            }
        } else {
            DeleteStatus = "Parking spot not exist!";
        }
        return DeleteStatus;
    }

    @GetMapping(path = "/GetParkingSpotInfo/{id}")
    public @ResponseBody ParkingSpots getParkingSpotInfo(@PathVariable int id) {
        ParkingSpots parkingSpot = new ParkingSpots();
        if (parking_spotRepository.findById(id).isPresent() == true) {
            parkingSpot.IdParkingSpot = parking_spotRepository.findById(id).get().GetIdParkingSpot();
            parkingSpot.ParkingSpotName = parking_spotRepository.findById(id).get().GetParkingSpotName();
            parkingSpot.StatusParkingSpot = parking_spotRepository.findById(id).get().GetStatusParkingSpot();
        }
        return parkingSpot;
    }

    @PutMapping(path = "/UpdateSatatusParkingSpot/{id}/{status}")
    public @ResponseBody String UpdateStatusParkingSpot(@PathVariable int id, @PathVariable String status) {
        String UpdateStatus = null;
        Optional<Parking_spot> existingParkSpot = parking_spotRepository.findById(id);
        if (existingParkSpot.isPresent()) {
            existingParkSpot.get().SetStatusParkingSpot(status);
            parking_spotRepository.save(existingParkSpot.get());
            UpdateStatus = "Status is change!";
        } else {
            UpdateStatus = "Status is not change!";
        }
        return UpdateStatus;
    }

    @PutMapping(path = "/CreateParkingSpot/{username}/{ParkingSpotName}/{status}")
    public @ResponseBody String CreateParkingSpot(@PathVariable String username,
            @PathVariable String ParkingSpotName,
            @PathVariable String status) {
        String CreateMySqlMessage = null;
        Parking_spot parking_spot = new Parking_spot();
        int companyId = 0;
        for (int i = 0; i < userRepository.count(); i++) {
            if (userRepository.findAll().get(i).GetUsername().equals(username) == true) {
                companyId = user_InformationRepository.findAll().get(i).GetCompanyId();
            }
        }
        Boolean cheakName = false;
        for (int i = 0; i < parking_spotRepository.count(); i++) {
            if (parking_spotRepository.findAll().get(i).GetParkingSpotName().equals(ParkingSpotName)) {
                cheakName = true;
            }
        }
        if (cheakName != true) {
            parking_spot.SetParkingSpotName(ParkingSpotName);
            parking_spot.SetCompanyId(companyId);
            parking_spot.SetStatusParkingSpot(status);

            parking_spotRepository.save(parking_spot);
            CreateMySqlMessage = "Parking spot is create!";
        } else {
            CreateMySqlMessage = "Parking spot is not change!";
        }
        return CreateMySqlMessage;
    }

}
