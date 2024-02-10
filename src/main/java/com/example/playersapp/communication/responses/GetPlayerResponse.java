/*
 * @author Eli Aviv
 */
package com.example.playersapp.communication.responses;

import com.example.playersapp.data.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetPlayerResponse extends RestResponse {
    private Player player;
}