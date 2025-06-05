package com.example.chat




sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object AddContact : NavRoutes("add_contact")
    data object Contacts : NavRoutes("contacts")
    data object Profile : NavRoutes("profile")
    data object OtpVerification : NavRoutes("otp_verification/{contact}")
    data object EmergencyHelpline : NavRoutes("emergency_helpline")
    data object  sos : NavRoutes("sos")
    data object Splash : NavRoutes("splash")
    data object ProfileOptions : NavRoutes("profile_options")
    data object EditProfile : NavRoutes("edit_profile")
    data object Journey : NavRoutes("journey")

    fun createRoute(contact: String): String {
                return "otp_verification/$contact"
            }
        }

