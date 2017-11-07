package ui.views

import ui.activities.LogInActivity
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatEditText
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.charly.visitapp.R
import extensions.*
import model.User
import org.jetbrains.anko.*
import ui.ViewBinder

/**
 * Created by Santiago Cirillo on 11/3/17.
 */

class LogInView : ViewBinder<LogInActivity, User, Boolean> {
    // UI Refs
    lateinit var mEmail: AppCompatEditText
    lateinit var mPassword: AppCompatEditText
    lateinit var spinner: ProgressBar
    lateinit var mTilemail: TextInputLayout
    lateinit var mTilPassword: TextInputLayout

    override fun bind(t: LogInActivity, u: User?, b: Boolean?): View {
        return t.UI {
            relativeLayout {
                lparams(width = matchParent, height = matchParent)
                backgroundResource = R.color.background_list

                textView {
                    id = R.id.appTitleTextView
                    visibility = View.GONE
                    textResource = R.string.app_title
                    textSize = 25f
                    textColor = color(R.color.defaultTextColor)
                    typeface = bold
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerHorizontally()
                }

                relativeLayout {
                    linearLayout {
                        id = R.id.singInLogo
                            layoutParams = LinearLayout.LayoutParams(
                                    dip(250), dip(250))
                        imageView{
                            image = drawable(R.drawable.logo_drselby)
                        }

                    }.lparams {
                        topMargin = dip(10)
                        centerHorizontally()
                    }
                    linearLayout {
                        id = R.id.signInUsernameWrapper
                        mTilemail = textInputLayout {
                            layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT)

                            setErrorTextAppearance(R.style.AppTextAppearanceError)

                            mEmail = appCompatEditText {
                                hintResource = R.string.sign_in_email_hint
                                textColor = color(R.color.defaultTextColor)
                                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                                singleLine = true
                                textSize = 18f
                                textChangedListener {
                                    onTextChanged { _, _, _, _ -> mTilemail.isErrorEnabled = false
                                    }
                                }
                            }
                        }

                    }.lparams {
                        topMargin = dip(20)
                        width = dip(250)
                        centerHorizontally()
                        below(R.id.singInLogo)
                    }

                    linearLayout {
                        id = R.id.signInPasswordWrapper
                        mTilPassword = textInputLayout {
                            layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT)

                            setErrorTextAppearance(R.style.AppTextAppearanceError)
                            isPasswordVisibilityToggleEnabled = true

                            mPassword = appCompatEditText {
                                id = R.id.signInUsernameWrapper
                                hintResource = R.string.sign_in_password_hint
                                singleLine = true
                                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                                textColor = color(R.color.defaultTextColor)
                                textSize = 18f
                                textChangedListener {
                                    onTextChanged { text, start, before, count ->
                                        mTilPassword.isErrorEnabled = false
                                    }
                                }
                            }
                        }
                    }.lparams {
                        width = dip(250)
                        centerHorizontally()
                        topMargin = dip(30)
                        below(R.id.signInUsernameWrapper)
                    }

                    styledButton {
                        id = R.id.signInButton
                        textResource = R.string.sign_in_button
                        typeface = semibold
                        backgroundResource = R.drawable.selector_button_dark
                        onClick {
                            handleOnSignInButtonPressed(
                                    ui = t,
                                    username = mEmail.text.toString(),
                                    password = mPassword.text.toString())
                        }
                    }.lparams {
                        width = dip(130)
                        topMargin = dip(30)
                        below(R.id.signInPasswordWrapper)
                        centerHorizontally()
                    }

                    textView {
                        id = R.id.forgotYourPassword
                        textResource = R.string.forgot_your_password_text
                        textSize = 14f
                        textColor = color(R.color.defaultTextColor)
                        typeface = semibold
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(20)
                        below(R.id.signInButton)
                        centerHorizontally()
                    }

                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    horizontalPadding = dip(10)
                    centerInParent()
                }

                spinner = progressBar().lparams {
                    centerInParent()
                    width = wrapContent
                    height = wrapContent
                }
                spinner.visibility = View.INVISIBLE
            }
        }.view
    }

    override fun unbind(t: LogInActivity) {
    }

    private fun handleOnSignInButtonPressed(ui: LogInActivity, username: String, password: String) {
        when (checkUserFields(username, password)) {
            LoginError.EMAIL_ERROR -> {
                mTilemail.error = ui.ctx.getString(R.string.error_invalid_email)
                mEmail.requestFocus()
            }
            LoginError.PASSWORD_ERROR -> {
                mTilPassword.error = ui.ctx.getString(R.string.error_invalid_password)
                mPassword.requestFocus()
            }else-> {
                var user = User()
                user.mEmail = username
                user.mPassword = password
                ui.authorizeUser(user)
            }
        }
    }
}
