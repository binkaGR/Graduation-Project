/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.parkingserver;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author binka
 */
public interface UserRepository extends JpaRepository<Users, Integer> {

}
