Feature: Verify that the filters in SRP are working properly

  Background: The user is already in used Cars Frame
    Given The user accept the cookies
    And The user populate the Location as bellow
      | State           |
      | South Australia |

  Scenario: Verify if the filter works properly
    Given The user set the filter as bellow
      | model   | price | kilometers |
      | A-CLASS | 60000 | 20000      |
    When The user sort the displayed result by "Price (descending)"
    Then The results should Match with the applied filter