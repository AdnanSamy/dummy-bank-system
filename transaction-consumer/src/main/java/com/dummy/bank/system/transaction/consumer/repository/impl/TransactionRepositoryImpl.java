package com.dummy.bank.system.transaction.consumer.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.dummy.bank.system.kafka.model.Transaction;
import com.dummy.bank.system.transaction.consumer.repository.TransactionRepository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private DataSource dataSource;

    public TransactionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Transaction transaction) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            StringBuffer sb = new StringBuffer();
            sb.append(
                    "insert into transaction(fromName, fromaccount, toname, toaccount, amount, currency, datetime) \n");
            sb.append(" values(?,?,?,?,?,?,?) ");

            pst = conn.prepareStatement(sb.toString());
            pst.setString(1, transaction.getFrom());
            pst.setString(2, transaction.getFromAccount());
            pst.setString(3, transaction.getTo());
            pst.setString(4, transaction.getToAccount());
            pst.setLong(5, transaction.getAmount());
            pst.setString(6, transaction.getCurrency());
            pst.setLong(7, transaction.getDatetime());
            pst.execute();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id, fromname, fromaccount, toname, \n");
            sb.append("	amount, currency, datetime, toaccount \n");
            sb.append("	FROM transaction; \n");
            pst = conn.prepareStatement(sb.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setAmount(rs.getLong("amount"));
                transaction.setCurrency(rs.getString("currency"));
                transaction.setDatetime(rs.getLong("datetime"));
                transaction.setFrom(rs.getString("fromname"));
                transaction.setFromAccount(rs.getString("fromaccount"));
                transaction.setTo(rs.getString("toname"));
                transaction.setToAccount(rs.getString("toaccount"));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                pst.close();
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

    @Override
    public List<Transaction> getByCurrency() {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT currency, SUM(amount) AS amount FROM transaction \n");
            sb.append("	GROUP BY currency \n");
            pst = conn.prepareStatement(sb.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setAmount(rs.getLong("amount"));
                transaction.setCurrency(rs.getString("currency"));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                pst.close();
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

}
