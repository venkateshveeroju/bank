

variable "project_name" {
  type        = string
  description = "Project name"
  default     = "myBankApp"

  validation {
    condition     = can(regex("^[a-zA-Z0-9]*$", var.project_name))
    error_message = "The project_name must be alphanumeric."
  }
}

variable "image_name" {
  type        = string
  description = "Image name"
  default     = "Bank beta"

  validation {
    condition     = can(regex("^[a-zA-Z0-9]*$", var.image_name))
    error_message = "The image_name must be alphanumeric."
  }
}

variable "resource_group_location" {
  type        = string
  description = "Location for all resources."
  default     = "eastus"
}

variable "resource_group_name_prefix" {
  type        = string
  description = "Prefix of the resource group name that's combined with a random ID so name is unique in your Azure subscription."
  default     = "rg"
}