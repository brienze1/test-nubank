Feature: Operation

  Scenario: Return no taxes when operation value is less than 20000
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
           {"operation":"sell", "unit-cost":15.00, "quantity": 50},
           {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00}] |

  Scenario: Return taxes when operation makes profit and no tax when it represents a loss
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
          {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
          {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 10000.00},{"tax": 0.00}] |

  Scenario: Receive two lines and return tax independently one from another
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
          {"operation":"sell", "unit-cost":15.00, "quantity": 50},
          {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
      """
    And also the following operation:
      """
        [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
        {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00}] |
      | [{"tax": 0.00},{"tax": 10000.00},{"tax": 0.00}] |

  Scenario: Return taxes computing loss from the first operation
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
          {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
          {"operation":"sell", "unit-cost":20.00, "quantity": 3000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 1000.00}] |

  Scenario: Return taxes computing weighted average price from the buy operations
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
          {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
          {"operation":"sell", "unit-cost":15.00, "quantity": 10000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00}] |

  Scenario: Return taxes computing weighted average price from the buy operations with profit only in the second sell
    Given the following operation:
      """
        [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
        {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
        {"operation":"sell", "unit-cost":15.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":25.00, "quantity": 5000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00},{"tax": 10000.00}] |

  Scenario: Return taxes retrieving loss from multiple sell operation
    Given the following operation:
      """
         [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
         {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
         {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
         {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
         {"operation":"sell", "unit-cost":25.00, "quantity": 1000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00},{"tax": 0.00},{"tax": 3000.00}] |

  Scenario: Calculate tax after sell of every stock bought in the first operation
    Given the following operation:
      """
        [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
        {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
        {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
        {"operation":"sell", "unit-cost":25.00, "quantity": 1000},
        {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":15.00, "quantity": 5000},
        {"operation":"sell", "unit-cost":30.00, "quantity": 4350},
        {"operation":"sell", "unit-cost":30.00, "quantity": 650}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax":0.00}, {"tax":0.00}, {"tax":0.00}, {"tax":0.00}, {"tax":3000.00}, {"tax":0.00}, {"tax":0.00}, {"tax":3700.00}, {"tax":0.00}] |

  Scenario: Calculate tax after sell of a part of the stock bought in the first operation
    Given the following operation:
      """
        [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":50.00, "quantity": 5000},
        {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
        {"operation":"sell", "unit-cost":50.00, "quantity": 15000}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax":0.00},{"tax":30000.00},{"tax":0.00},{"tax":100000.00}] |

