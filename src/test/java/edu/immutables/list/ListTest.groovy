package edu.immutables.list

import edu.immutables.list.impl.ImmutableList
import spock.lang.Specification
import spock.lang.Unroll

import static edu.immutables.list.impl.ImmutableList.emptyList

@Unroll
class ListTest extends Specification {

    def "Testing add operation"() {
        expect:
        list.size() == size

        where:
        list                                         || size
        emptyList()                                  || 0
        emptyList().addHead(1)                       || 1
        emptyList().addHead(1).addHead(2)            || 2
        emptyList().addHead(1).addHead(2).addHead(3) || 3
    }

    def "Test append operation"() {
        given:
        def immList1 = ImmutableList.of(list1)
        def immList2 = ImmutableList.of(list2)
        and:
        def immResultList = immList1.append(immList2).stream()
        def resultStream = resultList.stream()
        expect:
        immResultList.collect() == resultStream.collect()
        where:
        list1     || list2     || resultList
        []        || []        || []
        []        || [1, 2, 3] || [1, 2, 3]
        [1, 2, 3] || []        || [1, 2, 3]
        [1, 2, 3] || [4, 5, 6] || [1, 2, 3, 4, 5, 6]
        [1, 2]    || [3, 4, 5] || [1, 2, 3, 4, 5]
        [1, 2, 3] || [4, 5]    || [1, 2, 3, 4, 5]
    }

    def "Testing of(list) operation"() {
        given:
        def immList = ImmutableList.of(list)
        expect:
        immList.size() == size
        immList.getHead() == firstElement
        where:
        list      || size || firstElement
        [9]       || 1    || 9
        [8, 7]    || 2    || 8
        [7, 6, 5] || 3    || 7
    }

    def "Testing steam() operation"() {
        given:
        def stream = ImmutableList.of(list).stream()

        expect:
        stream.collect() == list

        where:
        list                 | _
        []                   | _
        [1]                  | _
        [2, 3]               | _
        [2, 3, 51, 213, 123] | _

    }

    def "Testing getHead operation"() {
        expect:
        immList.getHead() == element
        where:
        immList                   || element
        ImmutableList.of(1)       || 1
        ImmutableList.of(2, 1)    || 2
        ImmutableList.of(3, 2, 1) || 3
    }

    def "Testing get(ind) operation"() {
        given: "A list already contains multiple elements."
        def immList = ImmutableList.of([1, 2, 3, 4])

        expect:
        immList.get(ind) == element

        where:
        ind || element
        0   || 1
        1   || 2
        2   || 3
        3   || 4
    }

    def "Testing get(elem...) operation"() {
        given: "A list already contains multiple elements."
        def immList = ImmutableList.of(1, 2, 3, 4)

        expect:
        immList.get(ind) == element

        where:
        ind || element
        0   || 1
        1   || 2
        2   || 3
        3   || 4
    }

    def "Testing remove(elem) operation"() {
        given: "A list already contains multiple elements."
        def immList = ImmutableList.of(list)

        and:
        def immListAfterRemove = immList.remove(elem)

        expect:
        immListToList(immListAfterRemove) == listAfterRemove

        where:
        elem || list              || listAfterRemove
        132  || []                || []
        132  || [132, 1, 2, 3, 4] || [1, 2, 3, 4]
        132  || [1, 2, 3, 4, 132] || [1, 2, 3, 4]
        1    || [1, 2, 3, 1, 1]   || [2, 3, 1, 1]
        123  || [1, 2, 3]         || [1, 2, 3]
    }


    def "Testing removeAt(ind) operation"() {
        given: "A list already contains multiple elements."
        def immList = ImmutableList.of(list)

        and:
        def immListAfterRemove = immList.removeAt(ind)

        expect:
        immListToList(immListAfterRemove) == listAfterRemove

        where:
        ind || list              || listAfterRemove
        0   || [132, 1, 2, 3, 4] || [1, 2, 3, 4]
        4   || [1, 2, 3, 4, 132] || [1, 2, 3, 4]
        2   || [1, 2, 3, 1]      || [1, 2, 1]
    }

    def "Testing map operation"() {
        given: "A list already containing multiple elements."
        def list = [123, 456, 789, 1234, 1234, 2341]
        def immList = ImmutableList.of(list)
        def expStream = list.stream().map(mapFn)

        and:
        def immListStream = immList.map(mapFn).stream()

        expect:
        expStream.collect() == immListStream.collect()

        where:
        mapFn | _
                { a -> a - 10 } | _
                { a -> a * 12 } | _
                { a -> a + " Test Change Types" } | __
    }

    def immListToList(ImmutableList immList) {
        return immList.stream().collect()
    }
}
