package com.codewiz.java22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.FormatProcessor.FMT;

public class StringLiterals {

    public static void main(String[] args) throws SQLException {
        Account account = new Account("John Doe", 123456789, 1030450.0);
        // create string - Hello John Doe, your account id is 123456789 and balance is 1030450.0
        String message = FMT."""
            Hello \{account.name()}, your account id is \{account.accountId()}
            and balance is %,.1f\{account.balance()}
        """;
        System.out.println(message);




        Connection conn = null;
        /*String sql = """
                        SELECT *
                        FROM account
                        WHERE account_id = ?
                        AND balance > ?
                """;
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setLong(1, account.accountId());
        pst.setDouble(2, account.balance());
        pst.executeQuery();*/

        QueryProcessor QP = new QueryProcessor(conn);
        PreparedStatement pst = QP."""
                        SELECT *
                        FROM account
                        WHERE account_id = \{account.accountId()}
                        AND balance > \{account.balance()}
                """;
        pst.executeQuery();











        /*
        QueryProcessor QP = new QueryProcessor(conn);
        PreparedStatement pst = QP."""
                        SELECT *
                        FROM account
                        WHERE account_id = \{account.accountId()}
                        AND balance > \{account.balance()}
                """;
        pst.executeQuery();*/








    }
}


record Account(String name, long accountId, double balance) {
};