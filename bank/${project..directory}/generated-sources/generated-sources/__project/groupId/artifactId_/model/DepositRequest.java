package __project.groupId.artifactId_.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DepositRequest
 */
@Validated


public class DepositRequest   {
  @JsonProperty("accountNumber")
  private String accountNumber = null;

  @JsonProperty("depositAmount")
  private BigDecimal depositAmount = null;

  public DepositRequest accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  /**
   * Get accountNumber
   * @return accountNumber
   **/
  @Schema(description = "")
  
    public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public DepositRequest depositAmount(BigDecimal depositAmount) {
    this.depositAmount = depositAmount;
    return this;
  }

  /**
   * Get depositAmount
   * @return depositAmount
   **/
  @Schema(description = "")
  
    @Valid
    public BigDecimal getDepositAmount() {
    return depositAmount;
  }

  public void setDepositAmount(BigDecimal depositAmount) {
    this.depositAmount = depositAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepositRequest depositRequest = (DepositRequest) o;
    return Objects.equals(this.accountNumber, depositRequest.accountNumber) &&
        Objects.equals(this.depositAmount, depositRequest.depositAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber, depositAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepositRequest {\n");
    
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    depositAmount: ").append(toIndentedString(depositAmount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
