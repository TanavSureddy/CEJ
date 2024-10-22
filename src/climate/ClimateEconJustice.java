package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] data = inputLine.split(",");

        String stateName = data[2];
        StateNode statePointer = firstState;
        while (statePointer != null) {
            if (statePointer.getName().equals(stateName)) {
                return; 
            }
            statePointer = statePointer.getNext();
        }

        boolean ifFirstStateNull = false;
        StateNode newState = new StateNode(stateName, null, null);
    
        if (firstState == null) {
            firstState = newState;
        } else {
            statePointer = firstState;
            while (statePointer.getNext() != null) {
                statePointer = statePointer.getNext();
            }
            statePointer.setNext(newState);
        }
    }

   

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] data = inputLine.split(",");
        String stateName = data[2];
        String countyName = data[1];

        StateNode statePointer = firstState;
        CountyNode newCounty = new CountyNode(countyName, null, null);

        
        while(statePointer != null){
            if (statePointer.getName().equals(stateName)){ //copy paste from addToStateLevel method
                CountyNode countyPointer = statePointer.getDown();
                
                if (countyPointer == null){
                    statePointer.setDown(newCounty);
                    return;
                }
                boolean ifctyPtr = false;
                /* }if (countyPointer.getNext() != null && countyPointer.getName().equals(countyName )){
                    return;
                }else{
                    countyPointer = countyPointer.getNext();
                }
                return; */

                while (countyPointer.getNext() != null){
                    if (countyPointer.getName().equals(countyName)){
                        return;
                    }countyPointer = countyPointer.getNext();
                    
                    
                }
                boolean ctyPtrNull = (countyPointer.getName().equals(countyName));
                //after countyPointer becomes null (we have reached the end of the list)
                if(!countyPointer.getName().equals(countyName)){ //important
                    countyPointer.setNext(newCounty);}
                
            }
            statePointer = statePointer.getNext();
        }

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] data = inputLine.split(",");
        String commName = data[0];
        String countyName = data[1];
        String stateName = data[2];
        double percentAfricanAmerican = Double.parseDouble(data[3]);
        double percentNative = Double.parseDouble(data[4]);
        double percentAsian = Double.parseDouble(data[5]);
        double percentWhite = Double.parseDouble(data[8]);
        double percentHispanic = Double.parseDouble(data[9]);
        String isDisadvantaged = data[19];
        double pmLevel = Double.parseDouble(data[49]);
        double chanceOfFlood = Double.parseDouble(data[37]);
        double povertyLine = Double.parseDouble(data[121]);

        Data commData = new Data(percentAfricanAmerican, percentNative, percentAsian, percentWhite, percentHispanic, isDisadvantaged, pmLevel, chanceOfFlood, povertyLine);

        StateNode statePointer = firstState;
        while(statePointer != null){
            if (statePointer.getName().equals(stateName)){ //copy paste from addToStateLevel method
                CountyNode countyPointer = statePointer.getDown(); // paste from addToCountyLevel
                while (countyPointer != null){
                    if (countyPointer.getName().equals(countyName)){
                        CommunityNode commPointer = countyPointer.getDown();
                        CommunityNode newComm = new CommunityNode(commName, null, commData);
                        boolean ifCommPtrnull = false;
                        if (commPointer == null){
                            countyPointer.setDown(newComm);
                            return;
                        }
                        while(commPointer.getNext() != null){
                            if (commPointer.getName().equals(commName)){
                                return;
                            }
                            commPointer = commPointer.getNext();
                        }
                        boolean ifCommPtrThere = false;
                        if(!commPointer.getName().equals(commName)){
                            commPointer.setNext(newComm);
                        }return;

                    }
                    countyPointer = countyPointer.getNext();
                }
            }
            statePointer = statePointer.getNext();
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        // WRITE YOUR CODE HERE
        int numComms = 0;
        userPrcntage = userPrcntage / 100; 
        
        StateNode firstNode = firstState;
        if(firstNode == null){
            return 0;
        }
        for (StateNode statePointer = firstNode; statePointer != null; statePointer = statePointer.getNext()){
            CountyNode currCounty = statePointer.getDown();
            
            for (CountyNode countyPointer = currCounty; countyPointer != null; countyPointer = countyPointer.getNext()){
                CommunityNode currComm = countyPointer.getDown();

                for (CommunityNode commPointer = currComm; commPointer != null; commPointer = commPointer.getNext()){
                    boolean raceisAA = true;
                    if (race.equals("African American")){
                        if (commPointer.getInfo().prcntAfricanAmerican >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("True")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Native American")){
                        if (commPointer.getInfo().prcntNative >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("True")){  
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Asian American")){
                        if (commPointer.getInfo().prcntAsian >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("True")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("White American")){
                        if (commPointer.getInfo().prcntWhite >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("True")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Hispanic American")){
                        if (commPointer.getInfo().prcntHispanic >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("True")){
                                numComms++;
                            }
                        }
                    }

                }
            }
        }

        return numComms; // replace this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        //WRITE YOUR CODE HERE
        //literally same code as last but make it .equals("False")
        int numComms = 0;
        userPrcntage = userPrcntage / 100; 
        
        StateNode firstNode = firstState;
        if(firstNode == null){
            return 0;
        }
        for (StateNode statePointer = firstNode; statePointer != null; statePointer = statePointer.getNext()){
            CountyNode currCounty = statePointer.getDown();
            
            for (CountyNode countyPointer = currCounty; countyPointer != null; countyPointer = countyPointer.getNext()){
                CommunityNode currComm = countyPointer.getDown();

                for (CommunityNode commPointer = currComm; commPointer != null; commPointer = commPointer.getNext()){
                    boolean raceIsAA = true;
                    if (race.equals("African American")){
                        if (commPointer.getInfo().prcntAfricanAmerican >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("False")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Native American")){
                        if (commPointer.getInfo().prcntNative >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("False")){  
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Asian American")){
                        if (commPointer.getInfo().prcntAsian >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("False")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("White American")){
                        if (commPointer.getInfo().prcntWhite >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("False")){
                                numComms++;
                            }
                        }
                    }

                    if (race.equals("Hispanic American")){
                        if (commPointer.getInfo().prcntHispanic >= userPrcntage){
                            if (commPointer.getInfo().getAdvantageStatus().equals("False")){
                                numComms++;
                            }
                        }
                    }

                }
            }
        }

        return numComms; // replace this line

    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        // WRITE YOUR METHOD HERE
        ArrayList<StateNode> pollutedStates = new ArrayList<>();
        StateNode firstNode = firstState;
        if(firstNode == null){
            return pollutedStates;
        }
        for (StateNode statePointer = firstNode; statePointer != null; statePointer = statePointer.getNext()){
            CountyNode currCounty = statePointer.getDown();
            
            for (CountyNode countyPointer = currCounty; countyPointer != null; countyPointer = countyPointer.getNext()){
                CommunityNode currComm = countyPointer.getDown();

                for (CommunityNode commPointer = currComm; commPointer != null; commPointer = commPointer.getNext()){
                    boolean isPoluted = true;
                    if (commPointer.getInfo().getPMlevel() >= PMlevel){
                        if(!pollutedStates.contains(statePointer)){
                            pollutedStates.add(statePointer);}
                    }

                }
            }
        }

        return pollutedStates; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        // WRITE YOUR METHOD HERE
        int numComms = 0;
        StateNode firstNode = firstState;
        if(firstNode == null){
            return 0;
        }
        for (StateNode statePointer = firstNode; statePointer != null; statePointer = statePointer.getNext()){
            CountyNode currCounty = statePointer.getDown();
            
            for (CountyNode countyPointer = currCounty; countyPointer != null; countyPointer = countyPointer.getNext()){
                CommunityNode currComm = countyPointer.getDown();

                for (CommunityNode commPointer = currComm; commPointer != null; commPointer = commPointer.getNext()){
                    
                    if (commPointer.getInfo().getChanceOfFlood() >= userPercntage){
                        numComms++;
                    }

                }
            }
        }

        return numComms; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        //WRITE YOUR METHOD HERE
        ArrayList<CommunityNode> lowestIncomeComms = new ArrayList<>();
        StateNode statePointer = firstState;

        while (statePointer != null && !statePointer.getName().equals(stateName)){
            statePointer = statePointer.getNext();
        }
        if (statePointer != null){
            CountyNode countyPointer = statePointer.getDown();
            
            boolean countyPointerNull = false;
            while (countyPointer != null){
                CommunityNode commPointer = countyPointer.getDown();
                
                while (commPointer !=null){
                    if (lowestIncomeComms.size() <= 9){
                        lowestIncomeComms.add(commPointer);
                    }else{
                        boolean lICisFull = true;
                        //the arraylist has been filled with 10 comms
                        CommunityNode leastPovComm = lowestIncomeComms.get(0);
                        for (int i = 1; i < lowestIncomeComms.size(); i++){
                            if (lowestIncomeComms.get(i).getInfo().getPercentPovertyLine() < leastPovComm.getInfo().getPercentPovertyLine()){
                                leastPovComm = lowestIncomeComms.get(i);
                            }
                        }

                        //replace the richest comm in that list with one that is poorer than it
                        boolean setRichest = false;
                        if (commPointer.getInfo().getPercentPovertyLine() > leastPovComm.getInfo().getPercentPovertyLine()){
                            int richestComm = lowestIncomeComms.indexOf(leastPovComm);
                            lowestIncomeComms.set(richestComm, commPointer);
                        }
                    }
                    commPointer = commPointer.getNext();
                }
                countyPointer = countyPointer.getNext();
            }
            
        }
    
        return lowestIncomeComms; // replace this line
    }
}
    
