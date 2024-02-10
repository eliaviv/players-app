/*
 * @author Eli Aviv
 */
package com.example.playersapp.components;

import org.springframework.stereotype.Component;

@Component
public class Logger {

    // TODO: Infinite time - Could be extended to logInfo, logDebug, logTrace, logError, logWarning, etc
    // TODO: Infinite time - Could receive configuration for formatting logs

    public void log(String message) {
        System.out.println(message);
    }
}