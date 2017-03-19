package jp.lambdamagic.sql.query;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLSelectQueryTest {

    @Test(expected=NullArgumentException.class)
    public void SQLSelectQuery_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
        String tableName = null;
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }
    
    @Test
    public void SQLSelectQuery_acceptNullJoinClauses() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = null;
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }
    
    @Test
    public void SQLSelectQuery_acceptNullCondition() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = null;
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }
    
    @Test
    public void SQLSelectQuery_acceptNullSelectColumnNames() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = null;
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }
    
    @Test
    public void SQLSelectQuery_acceptNullGroupByColumnNames() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = null;
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }

    @Test
    public void SQLSelectQuery_acceptNullOrderByColumnMap() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = null;
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
    }
    
    @Test
    public void getTableName_returnGivenTableName() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getTableName(), is("test"));
    }
    
    @Test
    public void getJoinClauses_returnJoinClausesIfExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getJoinClauses().isPresent(), is(true));
        assertThat(query.getJoinClauses().get(), is(joinClauses));
    }
    
    @Test
    public void getJoinClauses_returnEmptyIfNotExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = null;
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getJoinClauses().isPresent(), is(false));
    }
    
    @Test
    public void getCondition_returnConditionIfExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getCondition().isPresent(), is(true));
        assertThat(query.getCondition().get(), is(condition));
    }
    
    @Test
    public void getCondition_returnEmptyIfNotExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = null;
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getCondition().isPresent(), is(false));
    }
    
    @Test
    public void getSelectColumnNames_returnSelectColumnNamesIfExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);

        assertThat(query.getSelectColumnNames().isPresent(), is(true));
        assertThat(query.getSelectColumnNames().get(), is(selectColumnNames));
    }
    
    @Test
    public void getSelectColumnNames_returnEmptyIfNotExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = null;
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getSelectColumnNames().isPresent(), is(false));
    }
    
    
    @Test
    public void getGroupByColumnNames_returnGroupByColumnNamesIfExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);

        assertThat(query.getGroupByColumnNames().isPresent(), is(true));
        assertThat(query.getGroupByColumnNames().get(), is(groupByColumnNames));
    }
    
    @Test
    public void getGroupByColumnNames_returnEmptyIfNotExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = null;
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getGroupByColumnNames().isPresent(), is(false));
    }
    
    @Test
    public void getOrderColumnMap_returnOrderColumnMapIfExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);

        assertThat(query.getOrderColumnMap().isPresent(), is(true));
        assertThat(query.getOrderColumnMap().get(), is(orderByColumnMap));
    }
    
    @Test
    public void getOrderColumnMap_returnEmptyIfNotExists() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = null;
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getOrderColumnMap().isPresent(), is(false));
    }
    
    @Test
    public void isOrderAscending_returnGivenOrderAscending() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.isOrderAscending(), is(true));
    }
    
    @Test
    public void getSkipCount_returnGivenSkipCount() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getSkipCount(), is(-1));
    }
    
    @Test
    public void getTakeCount_returnGivenTakeCount() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.getTakeCount(), is(-1));
    }
    
    @Test
    public void isDistinct_returnGivenDistinct() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.isDistinct(), is(false));
    }

    @Test
    public void isCount_returnGivenCount() {
        String tableName = "test";
        List<SQLJoinClause> joinClauses = new ArrayList<>();
        SQLCondition condition = SQLCondition.contain("name", "test");
        List<String> selectColumnNames = new ArrayList<>();
        List<String> groupByColumnNames = new ArrayList<>();
        Map<String, Boolean> orderByColumnMap = new HashMap<>();
        boolean orderAscending = true;
        int skipCount = -1;
        int takeCount = -1;
        boolean distinct = false;
        boolean count = false;
        SQLSelectQuery query = new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
        
        assertThat(query.isCount(), is(false));
    }
}
