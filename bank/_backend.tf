# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0.0"  # Ensure this is compatible with your resource needs
    }
  }
  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}
}

# Generate a random integer to create a globally unique name
resource "random_integer" "ri" {
  min = 10000
  max = 99999
}

# Create the resource group
resource "azurerm_resource_group" "rg" {
  name     = "myResourceGroup-${random_integer.ri.result}"
  location = "West Europe"
}

# Create an App Service Plan for Linux
resource "azurerm_app_service_plan" "appserviceplan" {
  name                = "webapp-asp-${random_integer.ri.result}"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"  # Use Linux OS

  sku {
    tier = "Basic"
    size = "B1"  # Changed to B1 as F1 is a free tier and may not be available in all regions
  }
}

# Create a Linux Web App with Java 17 runtime
resource "azurerm_linux_web_app" "webapp" {
  name                = "webapp-${random_integer.ri.result}"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  service_plan_id     = azurerm_service_plan.appserviceplan.id

  # Set the Java 17 runtime using linux_fx_version
  linux_fx_version    = "JAVA|17-java17"  # Java 17 runtime

  site_config {
    minimum_tls_version = "1.2"
  }

  app_settings = {
    "WEBSITE_RUN_FROM_PACKAGE" = "1"
  }

  tags = {
    environment = "production"
  }
}
