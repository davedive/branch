package com.branch.csv;

import com.branch.UserLoanStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class UserStatusCsvReader {

    private static final String USER_ID = "user_id";
    private static final String STATUS = "status";

    private CSVParser parser;

    public UserStatusCsvReader(String userStatusFileName) {
        try {
            Reader userStatusCsvData = new FileReader(userStatusFileName);
            this.parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(userStatusCsvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, UserLoanStatus> parseUserStatusCsv() {
        //Read in user_status and store map to reference
        Map<Integer, UserLoanStatus> statusMap = new HashMap<>();
        for (CSVRecord csvRecord : parser) {
            Integer userId = Integer.parseInt(csvRecord.get(USER_ID));
            UserLoanStatus status = UserLoanStatus.valueOf(csvRecord.get(STATUS).toUpperCase());
            statusMap.put(userId, status);
        }
        return statusMap;
    }
}
