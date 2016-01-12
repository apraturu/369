public class Stats {
	public int numMessages;
	public int numUniqueUsers;
	public double avgLengthWords;
	public double avgLengthWordsStandardDeviation;
	public double avgLengthCharacters;
	public double avgLengthCharactersStandardDeviation;

	public int numPublicMessages;
	public int numProtectedMessages;
	public int numPrivateMessages;
	public int numMessagesToAll;
	public int numMessagesToSelf;
	public int numMessagesToSubscribers;
	public int numMessagesToUserId;
	public int numResponseMessages;
	public int numNotResponseMessages;
	public int messageFrequency2_6;
	public int messageFrequency7_11;
	public int messageFrequency12_16;
	public int messageFrequency17_20;

    public double avgLengthWordsPublic;
    public double avgLengthWordsPublicStandardDeviation;
    public double avgLengthCharactersPublic;
    public double avgLengthCharactersPublicStandardDeviation;
    public double avgLengthWordsProtected;
    public double avgLengthWordsProtectedStandardDeviation;
    public double avgLengthCharactersProtected;
    public double avgLengthCharactersProtectedStandardDeviation;
    public double avgLengthWordsPrivate;
    public double avgLengthWordsPrivateStandardDeviation;
    public double avgLengthCharactersPrivate;
    public double avgLengthCharactersPrivateStandardDeviation;

    public double avgLengthWordsAll;
    public double avgLengthWordsAllStandardDeviation;
    public double avgLengthCharactersAll;
    public double avgLengthCharactersAllStandardDeviation;
    public double avgLengthWordsSelf;
    public double avgLengthWordsSelfStandardDeviation;
    public double avgLengthCharactersSelf;
    public double avgLengthCharactersSelfStandardDeviation;
    public double avgLengthWordsSubscribers;
    public double avgLengthWordsSubscribersStandardDeviation;
    public double avgLengthCharactersSubscribers;
    public double avgLengthCharactersSubscribersStandardDeviation; 
    public double avgLengthWordsUserId;
    public double avgLengthWordsUserIdStandardDeviation;
    public double avgLengthCharactersUserId;
    public double avgLengthCharactersUserIdStandardDeviation; 

    public double avgLengthWordsResponse;
    public double avgLengthWordsResponseStandardDeviation;
    public double avgLengthCharactersResponse;
    public double avgLengthCharactersResponseStandardDeviation;
    public double avgLengthWordsNotResponse;
    public double avgLengthWordsNotResponseStandardDeviation;
    public double avgLengthCharactersNotResponse;
    public double avgLengthCharactersNotResponseStandardDeviation;

    public int frequencyPublic_All;
    public int frequencyPublic_Self;
    public int frequencyPublic_Subscribers;
    public int frequencyPublic_UserId;
    public int frequencyPublic_PresentResponseFlag;
    public int frequencyPublic_AbsentResponseFlag;

    public int frequencyProtected_All;
    public int frequencyProtected_Self;
    public int frequencyProtected_Subscribers;
    public int frequencyProtected_UserId;
    public int frequencyProtected_PresentResponseFlag;
    public int frequencyProtected_AbsentResponseFlag;

    public int frequencyPrivate_All;
    public int frequencyPrivate_Self;
    public int frequencyPrivate_Subscribers;
    public int frequencyPrivate_UserId;
    public int frequencyPrivate_PresentResponseFlag;
    public int frequencyPrivate_AbsentResponseFlag;

    public int frequencyAll_PresentResponseFlag;
    public int frequencyAll_AbsentResponseFlag;
    public int frequencySelf_PresentResponseFlag;
    public int frequencySelf_AbsentResponseFlag;
    public int frequencySubscribers_PresentResponseFlag;
    public int frequencySubscribers_AbsentResponseFlag;
    public int frequencyUserId_PresentResponseFlag;
    public int frequencyUserId_AbsentResponseFlag;

    public int getNumMessages() {
      return numMessages;
   }

   public int getNumUniqueUsers() {return numUniqueUsers;}
	public double getAvgLengthWords() {return avgLengthWords;}
	public double getAvgLengthWordsStandardDeviation() {return avgLengthWordsStandardDeviation;}
	public double getAvgLengthCharacters() {return avgLengthCharacters;}
	public double getAvgLengthCharactersStandardDeviation() {return avgLengthCharactersStandardDeviation;}

	public int getNumPublicMessages() {return numPublicMessages;}
	public int getNumProtectedMessages() {return numProtectedMessages;}
	public int getNumPrivateMessages() {return numPrivateMessages;}
	public int getNumMessagesToAll() {return numMessagesToAll;}
	public int getNumMessagesToSelf() {return numMessagesToSelf;}
	public int getNumMessagesToSubscribers() {return numMessagesToSubscribers;}
	public int getNumMessagesToUserId() {return numMessagesToUserId;}
	public int getNumResponseMessages() {return numResponseMessages;}
	public int getNumNotResponseMessages () {return numNotResponseMessages;}
	public int getMessageFrequency2_6 () {return messageFrequency2_6;}
	public int getMessageFrequency7_11 () {return messageFrequency7_11;}
	public int getMessageFrequency12_16 () {return messageFrequency12_16;}
	public int getMessageFrequency17_20 () {return messageFrequency17_20;}

    public double getALengthWordsPublic () {return avgLengthWordsPublic;}
    public double getAvgLengthWordsPublicStandardDeviation () {return avgLengthWordsPublicStandardDeviation;}
    public double getAvgLengthCharactersPublic () {return avgLengthCharactersPublic;}
    public double getAvgLengthCharactersPublicStandardDeviation () {return avgLengthCharactersPublicStandardDeviation;}
    public double getAvgLengthWordsProtected () {return avgLengthWordsProtected;}
    public double getAvgLengthWordsProtectedStandardDeviation () {return avgLengthWordsProtectedStandardDeviation;}
    public double getAvgLengthCharactersProtected () {return avgLengthCharactersProtected;}
    public double getAvgLengthCharactersProtectedStandardDeviation () {return avgLengthCharactersProtectedStandardDeviation;}
    public double getAvgLengthWordsPrivate () {return avgLengthWordsPrivate;}
    public double getAvgLengthWordsPrivateStandardDeviation () {return avgLengthWordsPrivateStandardDeviation;}
    public double getAvgLengthCharactersPrivate () {return avgLengthCharactersPrivate;}
    public double getAvgLengthCharactersPrivateStandardDeviation () {return avgLengthCharactersPrivateStandardDeviation;}

    public double getAvgLengthWordsAll () {return avgLengthWordsAll;}
    public double getAvgLengthWordsAllStandardDeviation () {return avgLengthWordsAllStandardDeviation;}
    public double getAvgLengthCharactersAll () {return avgLengthCharactersAll;}
    public double getAvgLengthCharactersAllStandardDeviation () {return avgLengthCharactersAllStandardDeviation;}
    public double getAvgLengthWordsSelf () {return avgLengthWordsSelf;}
    public double getAvgLengthWordsSelfStandardDeviation () {return avgLengthWordsSelfStandardDeviation;}
    public double getAvgLengthCharactersSelf () {return avgLengthCharactersSelf;}
    public double getAvgLengthCharactersSelfStandardDeviation () {return avgLengthCharactersSelfStandardDeviation;}
    public double getAvgLengthWordsSubscribers () {return avgLengthWordsSubscribers;}
    public double getAvgLengthWordsSubscribersStandardDeviation () {return avgLengthWordsSubscribersStandardDeviation;}
    public double getAvgLengthCharactersSubscribers () {return avgLengthCharactersSubscribers;}
    public double getAvgLengthCharactersSubscribersStandardDeviation () {return avgLengthCharactersSubscribersStandardDeviation;} 
    public double getAvgLengthWordsUserId () {return avgLengthWordsUserId;}
    public double getAvgLengthWordsUserIdStandardDeviation () {return avgLengthWordsUserIdStandardDeviation;}
    public double getAvgLengthCharactersUserId () {return avgLengthCharactersUserId;}
    public double getAvgLengthCharactersUserIdStandardDeviation () {return avgLengthCharactersUserIdStandardDeviation;} 

    public double getAvgLengthWordsResponse () {return avgLengthWordsResponse;}
    public double getAvgLengthWordsResponseStandardDeviation () {return avgLengthWordsResponseStandardDeviation;}
    public double getAvgLengthCharactersResponse () {return avgLengthCharactersResponse;}
    public double getAvgLengthCharactersResponseStandardDeviation () {return avgLengthCharactersResponseStandardDeviation;}
    public double getAvgLengthWordsNotResponse () {return avgLengthWordsNotResponse;}
    public double getAvgLengthWordsNotResponseStandardDeviation () {return avgLengthWordsNotResponseStandardDeviation;}
    public double getAvgLengthCharactersNotResponse () {return avgLengthCharactersNotResponse;}
    public double getAvgLengthCharactersNotResponseStandardDeviation () {return avgLengthCharactersNotResponseStandardDeviation;}

    public int getFrequencyPublic_All () {return frequencyPublic_All;}
    public int getFrequencyPublic_Self () {return frequencyPublic_Self;}
    public int getFrequencyPublic_Subscribers () {return frequencyPublic_Subscribers;}
    public int getFrequencyPublic_UserId () {return frequencyPublic_UserId;}
    public int getFrequencyPublic_PresentResponseFlag () {return frequencyPublic_PresentResponseFlag;}
    public int getFrequencyPublic_AbsentResponseFlag () {return frequencyPublic_AbsentResponseFlag;}

    public int getFrequencyProtected_All () {return frequencyProtected_All;}
    public int getFrequencyProtected_Self () {return frequencyProtected_Self;}
    public int getFrequencyProtected_Subscribers () {return frequencyProtected_Subscribers;}
    public int getFrequencyProtected_UserId () {return frequencyProtected_UserId;}
    public int getFrequencyProtected_PresentResponseFlag () {return frequencyProtected_PresentResponseFlag;}
    public int getFrequencyProtected_AbsentResponseFlag () {return frequencyProtected_AbsentResponseFlag;}

    public int getFrequencyPrivate_All () {return frequencyPrivate_All;}
    public int getFrequencyPrivate_Self () {return frequencyPrivate_Self;}
    public int getFrequencyPrivate_Subscribers () {return frequencyPrivate_Subscribers;}
    public int getFrequencyPrivate_UserId () {return frequencyPrivate_UserId;}
    public int getFrequencyPrivate_PresentResponseFlag () {return frequencyPrivate_PresentResponseFlag;}
    public int getFrequencyPrivate_AbsentResponseFlag () {return frequencyPrivate_AbsentResponseFlag;}

    public int getFrequencyAll_PresentResponseFlag () {return frequencyAll_PresentResponseFlag;}
    public int getFrequencyAll_AbsentResponseFlag () {return frequencyAll_AbsentResponseFlag;}
    public int getFrequencySelf_PresentResponseFlag () {return frequencySelf_PresentResponseFlag;}
    public int getFrequencySelf_AbsentResponseFlag () {return frequencySelf_AbsentResponseFlag;}
    public int getFrequencySubscribers_PresentResponseFlag () {return frequencySubscribers_PresentResponseFlag;}
    public int getFrequencySubscribers_AbsentResponseFlag () {return frequencySubscribers_AbsentResponseFlag;}
    public int getFrequencyUserId_PresentResponseFlag () {return frequencyUserId_PresentResponseFlag;}
    public int getFrequencyUserId_AbsentResponseFlag () {return frequencyUserId_AbsentResponseFlag;}

    public void print() {
    	//35
    	String leftAlignFormatInt = "| %-50s | %-5d |%n";
      String leftAlignFormatDouble = "| %-50s | %-5.2f |%n";


      System.out.println();
      System.out.format("+------------------------------------------------------------+%n");
      System.out.format("| MESSAGING APPLICATION STATS                                |%n");
      System.out.format("+------------------------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Number of Messages", numMessages);
      System.out.format(leftAlignFormatInt, "Unique Users", numUniqueUsers);
      System.out.format(leftAlignFormatDouble, "Average Length (Characters)", avgLengthCharacters);
      System.out.format(leftAlignFormatDouble, "Standard Deviation",
       avgLengthCharactersStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Average Length (Words)", avgLengthWords);
      System.out.format(leftAlignFormatDouble, "Standard Deviation",
       avgLengthWordsStandardDeviation);
      System.out.format("+------------------------------------------------------------+%n");
      System.out.println();

      System.out.format("+------------------------------------------------------------+%n");
      System.out.format("| MESSAGE TYPE HISTOGRAMS                                    |%n");
      System.out.format("+------------------------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "# of Public Messages", numPublicMessages);
      System.out.format(leftAlignFormatInt, "# of Protected Messages", numProtectedMessages);
      System.out.format(leftAlignFormatInt, "# of Private Messages", numPrivateMessages);
      System.out.format(leftAlignFormatInt, "# of Messages to All", numMessagesToAll);
      System.out.format(leftAlignFormatInt, "# of Messages to Self", numMessagesToSelf);
      System.out.format(leftAlignFormatInt, "# of Messages to Subscribers", numMessagesToSubscribers);
      System.out.format(leftAlignFormatInt, "# of Messages to Single User Id", numMessagesToUserId);
      System.out.format(leftAlignFormatInt, "# of Response Messages", numResponseMessages);
      System.out.format(leftAlignFormatInt, "# of Non-Response Messages", numNotResponseMessages);
      System.out.format(leftAlignFormatInt, "[2, 6]", messageFrequency2_6);
      System.out.format(leftAlignFormatInt, "[7, 11]", messageFrequency7_11);
      System.out.format(leftAlignFormatInt, "[12, 16]", messageFrequency12_16);
      System.out.format(leftAlignFormatInt, "[17, 20]", messageFrequency17_20);
     System.out.format("+------------------------------------------------------------+%n");
      System.out.println();

      System.out.format("+------------------------------------------------------------+%n");
      System.out.format("| STATS FOR SUBSETS OF MESSAGES                              |%n");
      System.out.format("+------------------------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble, "Avg Length of Public Messages (Characters)", avgLengthCharactersPublic);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersPublicStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Public Messages (Words)", avgLengthWordsPublic);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsPublicStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Protected Messages (Characters)", avgLengthCharactersProtected);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersProtectedStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Protected Messages (Words)", avgLengthWordsProtected);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsProtectedStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Private Messages (Characters)", avgLengthCharactersPrivate);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersPrivateStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Private Messages (Words)", avgLengthWordsPrivate);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsPrivateStandardDeviation);
      System.out.format("+------------------------------------------------------------+%n");

      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to All (Characters)", avgLengthCharactersAll);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersAllStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to All (Words)", avgLengthWordsAll);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsAllStandardDeviation);

      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Self ((Characters)", avgLengthCharactersSelf);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersSelfStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Self (Words)", avgLengthWordsSelf);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsSelfStandardDeviation);

      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Subscribers (Characters)", avgLengthCharactersSubscribers);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersSubscribersStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Subscribers (Words)", avgLengthWordsSubscribers);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsSubscribersStandardDeviation);

      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Single UserId (Characters)", avgLengthCharactersUserId);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersUserIdStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Avg Length of Messages to Single UserId (Words)", avgLengthWordsUserId);
      System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsUserIdStandardDeviation);

      System.out.format("+------------------------------------------------------------+%n");
      
     System.out.format(leftAlignFormatDouble, "Avg Length of Response Messages (Characters)", avgLengthCharactersResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersResponseStandardDeviation);
     System.out.format(leftAlignFormatDouble, "Avg Length of Response Messages (Words)", avgLengthWordsResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsResponseStandardDeviation);

     System.out.format(leftAlignFormatDouble, "Avg Length of Non-Response Messages (Characters)", avgLengthCharactersNotResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersNotResponseStandardDeviation);
     System.out.format(leftAlignFormatDouble, "Avg Length of Non-Response Messages (Words)", avgLengthWordsNotResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsNotResponseStandardDeviation);


      System.out.format("+------------------------------------------------------------+%n");
      System.out.println();

      System.out.format("+------------------------------------------------------------+%n");
      System.out.format("| CONDITIONAL HISTOGRAMS                                     |%n");
      System.out.format("+------------------------------------------------------------+%n");

      System.out.format(leftAlignFormatInt, "Frequency Public & All", frequencyPublic_All);
      System.out.format(leftAlignFormatInt, "Frequency Public & Self", frequencyPublic_Self);
      System.out.format(leftAlignFormatInt, "Frequency Public & Subscribers", frequencyPublic_Subscribers);
      System.out.format(leftAlignFormatInt, "Frequency Public & Single User Id", frequencyPublic_UserId);
      System.out.format(leftAlignFormatInt, "Frequency Public & Response Flag", frequencyPublic_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Public & No Response Flag", frequencyPublic_AbsentResponseFlag);

      System.out.format(leftAlignFormatInt, "Frequency Protected & All", frequencyProtected_All);
      System.out.format(leftAlignFormatInt, "Frequency Protected & Self", frequencyProtected_Self);
      System.out.format(leftAlignFormatInt, "Frequency Protected & Subscribers", frequencyProtected_Subscribers);
      System.out.format(leftAlignFormatInt, "Frequency Protected & Single User Id", frequencyProtected_UserId);
      System.out.format(leftAlignFormatInt, "Frequency Protected & Response Flag", frequencyProtected_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Protected & No Response Flag", frequencyProtected_AbsentResponseFlag);

      System.out.format(leftAlignFormatInt, "Frequency Private & All", frequencyPrivate_All);
      System.out.format(leftAlignFormatInt, "Frequency Private & Self", frequencyPrivate_Self);
      System.out.format(leftAlignFormatInt, "Frequency Private & Subscribers", frequencyPrivate_Subscribers);
      System.out.format(leftAlignFormatInt, "Frequency Private & Single User Id", frequencyPrivate_UserId);
      System.out.format(leftAlignFormatInt, "Frequency Private & Response Flag", frequencyPrivate_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Private & No Response Flag", frequencyPrivate_AbsentResponseFlag);
      System.out.format("+------------------------------------------------------------+%n");

      System.out.format(leftAlignFormatInt, "Frequency All & Present Response Flag", frequencyAll_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency All & Absent Response Flag", frequencyAll_AbsentResponseFlag);

      System.out.format(leftAlignFormatInt, "Frequency Self & Present Response Flag", frequencySelf_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Self & Absent Response Flag", frequencySelf_AbsentResponseFlag);

      System.out.format(leftAlignFormatInt, "Frequency Subscribers & Present Response Flag", frequencySubscribers_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Subscribers & Absent Response Flag", frequencySubscribers_AbsentResponseFlag);

      System.out.format(leftAlignFormatInt, "Frequency Single User Id & Present Response Flag", frequencyUserId_PresentResponseFlag);
      System.out.format(leftAlignFormatInt, "Frequency Single User Id & Absent Response Flag", frequencyUserId_AbsentResponseFlag);

      System.out.format("+------------------------------------------------------------+%n");
      System.out.println();

    } 
}

