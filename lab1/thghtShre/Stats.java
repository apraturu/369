public class Stats {
	int numMessages;
	int numUniqueUsers;
	double avgLengthWords;
	double avgLengthWordsStandardDeviation;
	double avgLengthCharacters;
	double avgLengthCharactersStandardDeviation;

	int numPublicMessages;
	int numProtectedMessages;
	int numPrivateMessages;
	int numMessagesToAll;
	int numMessagesToSelf;
	int numMessagesToSubscribers;
	int numMessagesToUserId;
	int numResponseMessages;
	int numNotResponseMessages;
	int messageFrequency2_6;
	int messageFrequency7_11;
	int messageFrequency12_16;
	int messageFrequency17_20;

    double avgLengthWordsPublic;
    double avgLengthWordsPublicStandardDeviation;
    double avgLengthCharactersPublic;
    double avgLengthCharactersPublicStandardDeviation;
    double avgLengthWordsProtected;
    double avgLengthWordsProtectedStandardDeviation;
    double avgLengthCharactersProtected;
    double avgLengthCharactersProtectedStandardDeviation;
    double avgLengthWordsPrivate;
    double avgLengthWordsPrivateStandardDeviation;
    double avgLengthCharactersPrivate;
    double avgLengthCharactersPrivateStandardDeviation;

    double avgLengthWordsAll;
    double avgLengthWordsAllStandardDeviation;
    double avgLengthCharactersAll;
    double avgLengthCharactersAllStandardDeviation;
    double avgLengthWordsSelf;
    double avgLengthWordsSelfStandardDeviation;
    double avgLengthCharactersSelf;
    double avgLengthCharactersSelfStandardDeviation;
    double avgLengthWordsSubscribers;
    double avgLengthWordsSubscribersStandardDeviation;
    double avgLengthCharactersSubscribers;
    double avgLengthCharactersSubscribersStandardDeviation; 
    double avgLengthWordsUserId;
    double avgLengthWordsUserIdStandardDeviation;
    double avgLengthCharactersUserId;
    double avgLengthCharactersUserIdStandardDeviation; 

    double avgLengthWordsResponse;
    double avgLengthWordsResponseStandardDeviation;
    double avgLengthCharactersResponse;
    double avgLengthCharactersResponseStandardDeviation;
    double avgLengthWordsNotResponse;
    double avgLengthWordsNotResponseStandardDeviation;
    double avgLengthCharactersNotResponse;
    double avgLengthCharactersNotResponseStandardDeviation;

    int frequencyPublic_All;
    int frequencyPublic_Self;
    int frequencyPublic_Subscribers;
    int frequencyPublic_UserId;
    int frequencyPublic_PresentResponseFlag;
    int frequencyPublic_AbsentResponseFlag;

    int frequencyProtected_All;
    int frequencyProtected_Self;
    int frequencyProtected_Subscribers;
    int frequencyProtected_UserId;
    int frequencyProtected_PresentResponseFlag;
    int frequencyProtected_AbsentResponseFlag;

    int frequencyPrivate_All;
    int frequencyPrivate_Self;
    int frequencyPrivate_Subscribers;
    int frequencyPrivate_UserId;
    int frequencyPrivate_PresentResponseFlag;
    int frequencyPrivate_AbsentResponseFlag;

    int frequencyAll_PresentResponseFlag;
    int frequencyAll_AbsentResponseFlag;
    int frequencySelf_PresentResponseFlag;
    int frequencySelf_AbsentResponseFlag;
    int frequencySubscribers_PresentResponseFlag;
    int frequencySubscribers_AbsentResponseFlag;
    int frequencyUserId_PresentResponseFlag;
    int frequencyUserId_AbsentResponseFlag;

    public void print() {
    	//35
    	String leftAlignFormatInt = "| %-50s | %-5d |%n";
      String leftAlignFormatDouble = "| %-50s | %-5.2f |%n";


      System.out.println();
      System.out.format("+---------------------------------------------+%n");
      System.out.format("| MESSAGING APPLICATION STATS                 |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Number of Messages", numMessages);
      System.out.format(leftAlignFormatInt, "Unique Users", numUniqueUsers);
      System.out.format(leftAlignFormatDouble, "Average Length (Characters)", avgLengthCharacters);
      System.out.format(leftAlignFormatDouble, "Standard Deviation",
       avgLengthCharactersStandardDeviation);
      System.out.format(leftAlignFormatDouble, "Average Length (Words)", avgLengthWords);
      System.out.format(leftAlignFormatDouble, "Standard Deviation",
       avgLengthWordsStandardDeviation);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| MESSAGE TYPE HISTOGRAMS                     |%n");
      System.out.format("+---------------------------------------------+%n");
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
      System.out.format(leftAlignFormatInt, "[7, 11)", messageFrequency7_11);
      System.out.format(leftAlignFormatInt, "[12, 16)", messageFrequency12_16);
      System.out.format(leftAlignFormatInt, "[17, 20)", messageFrequency17_20);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| STATS FOR SUBSETS OF MESSAGES               |%n");
      System.out.format("+---------------------------------------------+%n");
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
      System.out.format("+---------------------------------------------+%n");

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

      System.out.format("+---------------------------------------------+%n");
      
     System.out.format(leftAlignFormatDouble, "Avg Length of Response Messages (Characters)", avgLengthCharactersResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersResponseStandardDeviation);
     System.out.format(leftAlignFormatDouble, "Avg Length of Response Messages (Words)", avgLengthWordsResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsResponseStandardDeviation);

     System.out.format(leftAlignFormatDouble, "Avg Length of Non-Response Messages (Characters)", avgLengthCharactersNotResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthCharactersNotResponseStandardDeviation);
     System.out.format(leftAlignFormatDouble, "Avg Length of Non-Response Messages (Words)", avgLengthWordsNotResponse);
     System.out.format(leftAlignFormatDouble, "Standard Deviation", avgLengthWordsNotResponseStandardDeviation);


      System.out.format("+---------------------------------------------+%n");
      System.out.println();

       System.out.format("+---------------------------------------------+%n");
      System.out.format("| STATS FOR SUBSETS OF MESSAGES               |%n");
      System.out.format("+---------------------------------------------+%n");

      System.out.format("+---------------------------------------------+%n");
      System.out.println();

    }
      
}

