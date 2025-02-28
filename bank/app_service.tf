resource "azurerm_service_plan" "backend" {
  name                = "${var.project_name}-appserviceplan"
  resource_group_name = azurerm_resource_group.backend-app.name
  location            = azurerm_resource_group.backend-app.location
  os_type             = "Linux"
  sku_name            = "P1v2"
}

resource "azurerm_linux_web_app" "backend" {
  name                = "${var.project_name}-webapp"
  resource_group_name = azurerm_resource_group.backend-app.name
  location            = azurerm_service_plan.backend.location
  service_plan_id     = azurerm_service_plan.backend.id

  site_config {
    always_on = "true"

    application_stack {
      docker_image     = "${azurerm_container_registry.acr.login_server}/${var.image_name}:latest"
      docker_image_tag = "latest"
      dotnet_version   = "6.0"
    }
  }

  app_settings = {
    "DOCKER_REGISTRY_SERVER_URL"      = "https://${azurerm_container_registry.acr.login_server}"
    "DOCKER_REGISTRY_SERVER_USERNAME" = azurerm_container_registry.acr.admin_username
    "DOCKER_REGISTRY_SERVER_PASSWORD" = azurerm_container_registry.acr.admin_password
  }
}