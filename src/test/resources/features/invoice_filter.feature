Feature: Filter Invoices by Status

  As a user
  I want to filter invoices by their status
  So that I can quickly find invoices based on their payment status

  Scenario Outline: Filter invoices by status
    When the user hovers over the "<status>" status filter
    Then only invoices with "<status>" status should be displayed


    Examples:
      | status  |
      | Paid    |
      | Pending |
      | Draft   |

  Scenario: Remove filter to see all invoices
    Given the user has applied a status filter
    When the user removes the filter
    Then invoices with various statuses should be displayed


