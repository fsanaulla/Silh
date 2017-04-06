package module

import _root_.services.UserService
import com.google.inject.AbstractModule
import com.mohiva.play.silhouette.api.EventBus
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util._
import com.mohiva.play.silhouette.impl.util.{DefaultFingerprintGenerator, SecureRandomIDGenerator}
import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import models.User
import net.codingwell.scalaguice.ScalaModule
import play.api.libs.concurrent.Execution.Implicits._

/**
  * Created by faiaz on 06.04.17.
  */
class Module extends AbstractModule with ScalaModule {
  //todo: configure module
  override def configure(): Unit = {
    bind[IdentityService[User]].to[UserService]
//    bind[DelegableAuthInfoDAO[PasswordInfo]].to[PasswordInfoDao]
//    bind[DelegableAuthInfoDAO[OAuth1Info]].to[OAuth1InfoDao]
    bind[IDGenerator].toInstance(new SecureRandomIDGenerator())
    bind[PasswordHasher].toInstance(new BCryptPasswordHasher)
    bind[FingerprintGenerator].toInstance(new DefaultFingerprintGenerator(false))
    bind[EventBus].toInstance(EventBus())
    bind[Clock].toInstance(Clock())
  }
}
