package com.credit.card.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test abstraction.
 * <p>
 * Each unit test is tagged with {@code unit},
 * so that it can be easily distinguished from other types of tests.
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitTest {
}
