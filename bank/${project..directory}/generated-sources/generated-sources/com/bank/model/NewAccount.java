package com.bank.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NewAccount
 */
@Validated


public class NewAccount   {
  @JsonProperty("balance")
  private Float balance = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("dob")
  private String dob = null;

  @JsonProperty("street")
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("state")
  private String state = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("postalCode")
  private BigDecimal postalCode = null;

  public NewAccount balance(Float balance) {
    this.balance = balance;
    return this;
  }

  /**
   * optional initial credit amount required
   * @return balance
   **/
  @Schema(description = "optional initial credit amount required")
  
    public Float getBalance() {
    return balance;
  }

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  public NewAccount firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "firstName", required = true, description = "")
      @NotNull

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public NewAccount lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "lastName", required = true, description = "")
      @NotNull

    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public NewAccount email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "abchfg@efg.com", required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public NewAccount password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "a123sd", required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public NewAccount dob(String dob) {
    this.dob = dob;
    return this;
  }

  /**
   * Get dob
   * @return dob
   **/
  @Schema(example = "12.23.2000", required = true, description = "")
      @NotNull

    public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public NewAccount street(String street) {
    this.street = street;
    return this;
  }

  /**
   * Get street
   * @return street
   **/
  @Schema(example = "street name", required = true, description = "")
      @NotNull

    public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public NewAccount city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   **/
  @Schema(example = "city name", required = true, description = "")
      @NotNull

    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public NewAccount state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   **/
  @Schema(example = "state name", required = true, description = "")
      @NotNull

    public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public NewAccount country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   **/
  @Schema(example = "country name", required = true, description = "")
      @NotNull

    public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public NewAccount postalCode(BigDecimal postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
   **/
  @Schema(example = "501510", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(BigDecimal postalCode) {
    this.postalCode = postalCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewAccount newAccount = (NewAccount) o;
    return Objects.equals(this.balance, newAccount.balance) &&
        Objects.equals(this.firstName, newAccount.firstName) &&
        Objects.equals(this.lastName, newAccount.lastName) &&
        Objects.equals(this.email, newAccount.email) &&
        Objects.equals(this.password, newAccount.password) &&
        Objects.equals(this.dob, newAccount.dob) &&
        Objects.equals(this.street, newAccount.street) &&
        Objects.equals(this.city, newAccount.city) &&
        Objects.equals(this.state, newAccount.state) &&
        Objects.equals(this.country, newAccount.country) &&
        Objects.equals(this.postalCode, newAccount.postalCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(balance, firstName, lastName, email, password, dob, street, city, state, country, postalCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewAccount {\n");
    
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
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
