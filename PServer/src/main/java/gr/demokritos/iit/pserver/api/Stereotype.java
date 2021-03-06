/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.pserver.api;

import gr.demokritos.iit.pserver.ontologies.Client;
import gr.demokritos.iit.pserver.storage.interfaces.IStereotypeStorage;
import gr.demokritos.iit.security.SecurityLayer;
import gr.demokritos.iit.security.authorization.Action;
import gr.demokritos.iit.security.authorization.Actions;
import gr.demokritos.iit.utilities.configuration.PServerConfiguration;
import gr.demokritos.iit.utilities.logging.Logging;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class implement the Stereotype mode of PServer. It supports the
 * stereotype user model. In this mode PS create groups of users with common
 * demographic user information (attributes)
 *
 * @author Panagiotis Giotis <giotis.p@gmail.com>
 */
public class Stereotype {

    private final IStereotypeStorage dbStereotype;
    private final PServerConfiguration psConfig;
    private final Client psClient;
    public static final Logger LOGGER = LoggerFactory.getLogger(Stereotype.class);
    private SecurityLayer security = new SecurityLayer();
    private final HashMap<String, Action> actions
            = new HashMap<>(new Actions().getStereotypeActions());

    public Stereotype(IStereotypeStorage dbStereotype, Client psClient) {
        this.psConfig = new PServerConfiguration();
        this.dbStereotype = dbStereotype;
        this.psClient = psClient;

        //Update logging level 
        Logging.updateLoggerLevel(Stereotype.class, psConfig.getLogLevel());
    }

    /**
     * Set security control for user authorization
     *
     * @param security
     */
    public void setSecurity(SecurityLayer security) {
        this.security = security;
    }

    /**
     * Get the system attributes based on user attributes
     *
     * @return A set of attribute names
     */
    public Set<String> getSystemAttributes() {

        //Check permission
        if (!getPermissionFor(actions.get("aGetSystemAttributes"), "R")) {
            LOGGER.error("Premission Denied");
            return null;
        }

        //Update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage getSystemAttributes to get PServer attributes
        return dbStereotype.getSystemAttributes(psClient.username);
    }

    /**
     * Add stereotype in PServer
     *
     * @param stereotypeName The stereotype name
     * @param rule The stereotype rule
     * @return The success status of this action
     */
    public boolean addStereotype(String stereotypeName, String rule) {

        //Check permission
        if (!getPermissionFor(actions.get("aAddStereotype"), "W")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //Update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage AddStereotype to create stereotype on PServer 
        return dbStereotype.addStereotype(stereotypeName, rule, psClient.username);
    }

    /**
     * Delete stereotypes from PServer
     *
     * @param pattern The stereotype name pattern. If is null delete all
     * stereotypes
     * @return The success status of this action
     */
    public boolean deleteStereotypes(String pattern) {

        //Check permission
        if (!getPermissionFor(actions.get("aDeleteStereotypes"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }
        if (pattern != null) {
            if (pattern.isEmpty()) {
                LOGGER.error("Wrong pattern");
                return false;
            }
        }
        //Update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage deleteStereotype to delete stereotypes based on pattern
        return dbStereotype.deleteStereotypes(pattern, psClient.username);
    }

    /**
     * Get stereotype names
     *
     * @param pattern The stereotype name pattern. If is null return all the
     * stereotypes
     * @param page The page number. If is null return all the results on single
     * page
     * @return A set o stereotype names
     */
    public Set<String> getStereotypes(String pattern, Integer page) {

        //Check permission
        if (!getPermissionFor(actions.get("aGetStereotypes"), "R")) {
            LOGGER.error("Premission Denied");
            return null;
        }

        //Check if page is null or page <1
        if (page == null || page < 1) {
            //set page null to return single page
            page = null;
        }

        //Update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage getStereotypes to get PServer stereotypes
        return dbStereotype.getStereotypes(pattern, page, psClient.username).keySet();
    }

    /**
     * Remake Stereotype
     *
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean remakeStereotype(String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aRemakeStereotype"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage remakeStereotype to delete and remake the stereotype
        return dbStereotype.remakeStereotype(stereotypeName, psClient.username);
    }

    /**
     * Update stereotype features based on user profile who belong to stereotype
     *
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean updateStereotypeFeatures(String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aUpadateStereotypeFeatures"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();
        if (!dbStereotype.deleteStereotypeFeatures(stereotypeName, null, psClient.username)) {
            LOGGER.error("Error on delete stereotype features");
            return false;
        }
        //Call storage updateStereotypeFeatures to update all Stereotypes features
        return dbStereotype.updateStereotypeFeatures(stereotypeName, psClient.username);
    }

    /**
     * Add or remove stereotype users who satisfy the stereotype rule/
     *
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean updateStereotypeUsers(String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aUpadateStereotypeUsers"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage updateStereotypeUsers to update all Stereotypes Users
        return dbStereotype.updateStereotypeUsers(stereotypeName, psClient.username);
    }

    /**
     * Find and add the users who satisfies the stereotype rule
     *
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean findStereotypeUsers(String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aFindStereotypeUsers"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage findStereotypeUsers to find new users to full fill the stereotype rule
        return dbStereotype.findStereotypeUsers(stereotypeName, psClient.username);
    }

    /**
     * Find and remove the users who not satisfies the stereotype rule
     *
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean checkStereotypeUsers(String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aCheckStereotypeUsers"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage checkStereotypeUsers to check if there is any user 
        //who not full fill yet the stereotype rule
        return dbStereotype.checkStereotypeUsers(stereotypeName, psClient.username);
    }

    /**
     * Set manually the stereotype features
     *
     * @param stereotypeName The stereotype name
     * @param features A map with pairs of feature name - value
     * @return The success status of this action
     */
    public boolean setStereotypeFeatures(String stereotypeName,
            Map<String, String> features) {

        //Check permission
        if (!getPermissionFor(actions.get("aSetStereotypeFeatures"), "W")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call setStereotypeFeatures from storage 
        //to add features on stereotype profile
        return dbStereotype.setStereotypeFeatures(stereotypeName, features, psClient.username);
    }

    /**
     * Modify manually the stereotype features
     *
     * @param stereotypeName The stereotype name
     * @param features A map with pairs of feature name - value
     * @return The success status of this action
     */
    public boolean modifyStereotypeFeatures(String stereotypeName,
            Map<String, String> features) {

        //Check permission
        if (!getPermissionFor(actions.get("aModifyStereotypeFeatures"), "W")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call modifyStereotypeFeatures from storage 
        //to increase or decrease feature values on stereotype profile
        return dbStereotype.modifyStereotypeFeatures(stereotypeName, features, psClient.username);
    }

    /**
     * Get stereotype features
     *
     * @param stereotypeName The stereotype name
     * @param pattern The feature name pattern. If null return all the features
     * @param page The page number. If is null return all result on single page
     * @return A map with pairs of feature name - value
     */
    public Map<String, String> getStereotypeFeatures(String stereotypeName,
            String pattern, Integer page) {

        //Check permission
        if (!getPermissionFor(actions.get("aGetStereotypeFeatures"), "R")) {
            LOGGER.error("Premission Denied");
            return null;
        }

        //Check if page is null or page <1
        if (page == null || page < 1) {
            //set page null to return single page
            page = null;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage getStereotypeFeatures to get Stereotypes profile
        return dbStereotype.getStereotypeFeatures(stereotypeName, pattern, page, psClient.username);
    }

    /**
     * Delete features from stereotype
     *
     * @param stereotypeName The stereotype name
     * @param pattern The feature name pattern. If is null delete all features
     * @return The success status of this action
     */
    public boolean deleteStereotypeFeatures(String stereotypeName, String pattern) {

        //Check permission
        if (!getPermissionFor(actions.get("aDeleteStereotypeFeatures"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call deleteStereotypeFeatures to remove features from Stereotype Profile
        return dbStereotype.deleteStereotypeFeatures(stereotypeName, pattern, psClient.username);
    }

    /**
     * Get stereotype users
     *
     * @param stereotypeName The stereotype name
     * @param pattern The username pattern. If is null return all the users
     * @param page The page number. If page number is null return all the
     * results on single page
     * @return List with usernames
     */
    public List<String> getStereotypeUsers(String stereotypeName, String pattern,
            Integer page) {

        //Check permission
        if (!getPermissionFor(actions.get("aGetStereotypeUsers"), "R")) {
            LOGGER.error("Premission Denied");
            return null;
        }

        //Check if page is null or page <1
        if (page == null || page < 1) {
            //set page null to return single page
            page = null;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage getStereotypeUsers to get a list with users who belongs to stereotype
        return dbStereotype.getStereotypeUsers(stereotypeName, pattern, page, psClient.username);
    }

    /**
     * Get the stereotypes which user belongs
     *
     * @param username The username
     * @param pattern The stereotype name pattern. If pattern is null get all
     * stereotypes
     * @param page The page number. If is null return the results on single page
     * @return
     */
    public List<String> getUserStereotypes(String username, String pattern,
            Integer page) {

        //Check permission
        if (!getPermissionFor(actions.get("aGetUserStereotypes"), "R")) {
            LOGGER.error("Premission Denied");
            return null;
        }

        //Check if page is null or page <1
        if (page == null || page < 1) {
            //set page null to return single page
            page = null;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage getUserStereotype to get a list with Stereotypes tha user belong
        return dbStereotype.getUserStereotypes(username, pattern, page, psClient.username);
    }

    /**
     * Ad user manually on stereotype
     *
     * @param username The username
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean addUserOnStereotype(String username, String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aAddUserOnStereotype"), "W")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage addUserOnStereotype to add new user on Stereotype
        return dbStereotype.addUserOnStereotype(username, stereotypeName, psClient.username);
    }

    /**
     * Delete user manually from stereotype
     *
     * @param username The username
     * @param stereotypeName The stereotype name
     * @return The success status of this action
     */
    public boolean deleteUserFromStereotype(String username, String stereotypeName) {

        //Check permission
        if (!getPermissionFor(actions.get("aDeleteUserFromStereotype"), "X")) {
            LOGGER.error("Premission Denied");
            return false;
        }

        //update Authenticated time
        psClient.updateAuthenticatedTimestamp();

        //Call storage deleteUserFromStereotype to delete a user from Stereotype
        return dbStereotype.deleteUserFromStereotype(username, stereotypeName, psClient.username);
    }

    /**
     * Get the permission for the given action and client
     *
     * @param a The action that we want to check the permission
     * @param sAccessType The access type R (read) - W (write) - X (execute)
     * @return A true or false if the permission granted
     */
    public boolean getPermissionFor(Action a, String sAccessType) {

        return ((security != null)
                && (security.autho.getAccessRights(psClient, a).get(sAccessType))
                && (psClient.authenticatedTimestamp != 0));
    }

}
