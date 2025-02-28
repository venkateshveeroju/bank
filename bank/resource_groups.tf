resource "azurerm_resource_group" "hub" {
  name     = "${var.resource_group_name_prefix}-hub"
  location = var.resource_group_location
}

resource "azurerm_resource_group" "backend-app" {
  name     = "${var.resource_group_name_prefix}-backend"
  location = var.resource_group_location
}