package jp.lambdamagic.pipeline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.Tuple2;

public class PipelineTest {

    @Test(expected=NullArgumentException.class)
    public void from_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() {
        Pipeline.from((DataSource<?>)null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void from_mustThrowNullArgumentExceptionWhenNullSteamIsGiven() {
        Pipeline.from((Stream<?>)null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void from_mustThrowNullArgumentExceptionWhenNullIterableIsGiven() {
        Pipeline.from((Iterable<?>)null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void map_mustThrowNullArgumentExceptionWhenNullProcessorIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).map(null);
    }
    
    @Test
    public void map_mapPipelineDataIntoAnotherDataWithProcessor() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3))
                                        .map(number -> number + 1)
                                        .toList();
        
        assertThat(list, is(Arrays.asList(2, 3, 4)));
    }
    
    @Test(expected=NullArgumentException.class)
    public void forEach_mustThrowNullArgumentExceptionWhenNullProcessorIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).forEach((DataProcessor<Integer, ?>)null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void forEach_mustThrowNullArgumentExceptionWhenNullConsumerIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).forEach((Consumer<Integer>)null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void filter_mustThrowNullArgumentExceptionWhenNullPredicateIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).filter(null);
    }
    
    @Test
    public void filter_filterPipelineDataWithPredicate() throws Exception {
        List<Integer> list = Pipeline.from(IntStream.range(1, 10))
                                        .filter(number -> (number % 2 == 1))
                                        .toList();
        
        assertThat(list, is(Arrays.asList(1, 3, 5, 7, 9)));
    }
    
    @Test(expected=NullArgumentException.class)
    public void interleave_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).interleave((DataSource<Integer>[])null);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void interleave_interleaveDataSources() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3))
                                        .interleave(DataSource.asDataSource(4, 5, 6), DataSource.asDataSource(7, 8, 9))
                                        .toList();
        
        assertThat(list, is(Arrays.asList(1, 4, 7, 2, 5, 8, 3, 6, 9)));
    }
    
    @Test(expected=NullArgumentException.class)
    public void merge_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).merge((DataSource<Integer>[])null);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void merge_mergeDataSources() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3))
                                        .merge(DataSource.asDataSource(4, 5, 6), DataSource.asDataSource(7, 8, 9))
                                        .toList();
        
        assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
    
    @Test(expected=NullArgumentException.class)
    public void zip_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
        Pipeline.from(() -> Optional.of(1)).zip(null);
    }
    
    @Test
    public void zip_zipTwoDataSourcesIntoOne() throws Exception {
        List<Tuple2<Integer, Integer>> list = Pipeline.from(DataSource.asDataSource(1, 2, 3))
                                        .zip(DataSource.asDataSource(4, 5, 6))
                                        .toList();
        
        assertThat(list.size(), is(3));
        
        Tuple2<Integer, Integer> tuple = list.get(0);
        assertThat(tuple.getFirstValue(), is(1));
        assertThat(tuple.getSecondValue(), is(4));
        
        tuple = list.get(1);
        assertThat(tuple.getFirstValue(), is(2));
        assertThat(tuple.getSecondValue(), is(5));
        
        tuple = list.get(2);
        assertThat(tuple.getFirstValue(), is(3));
        assertThat(tuple.getSecondValue(), is(6));
    }
    
    @Test
    public void trim_trimDataSource() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3, 4, 5))
                                        .trim(3)
                                        .toList();
        
        assertThat(list, is(Arrays.asList(1, 2, 3)));
    }
    
    @Test
    public void trim_doNothingWhenSpecifiedCountIsLargerThanPipelineElement() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3, 4, 5))
                                        .trim(10)
                                        .toList();
        
        assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5)));
    }
    
    @Test
    public void fold_acceptNullSeedArgument() throws Exception {
        Pipeline.from(DataSource.asDataSource(1, 2, 3)).fold(null, (a, b) -> null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void fold_mustThrowNullFoldingFunctionArgumentException() throws Exception {
        Pipeline.from(DataSource.asDataSource(1, 2, 3)).fold(1, null);
    }
    
    @Test
    public void fold_foldPipelineElementsWithGivenFunction() throws Exception {
        int result = Pipeline.from(IntStream.range(1, 10)).fold(1, (a, b) -> a * b);
        assertThat(result, is(362880));
    }
    
    @Test
    public void repeat_repeatPipeline() throws Exception {
        List<Integer> list = Pipeline.from(DataSource.asDataSource(1, 2, 3, 4, 5)).repeat().trim(15).toList();
        assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5)));
    }

}
