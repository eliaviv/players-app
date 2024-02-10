/*
 * @author Eli Aviv
 */
package com.example.playersapp.communication.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String status;
    private String description;
}