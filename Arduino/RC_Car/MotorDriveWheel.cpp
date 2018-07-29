#include "MotorDriveWheel.h"

MotorDriveWheel::MotorDriveWheel(int pin, int speed) {
  m_motorPin = pin;

  m_motor = new AF_DCMotor(pin);

  m_direction = DRIVE_STOP;

  setSpeed(speed);

}

MotorDriveWheel::~MotorDriveWheel() {
  delete m_motor;
  m_motor = nullptr;
}

void MotorDriveWheel::setSpeed(int speed) {
  m_motor->setSpeed(speed);
}

void MotorDriveWheel::setDirection(int direction) {
  if (m_direction == direction){
    m_direction = DRIVE_STOP;
  }else
    m_direction = direction;

  update();
}

void MotorDriveWheel::update() {
  int setting;

  switch (m_direction) {
    case DRIVE_FORWARD:
      setting = FORWARD;
      break;
    case DRIVE_BACKWARD:
      setting = BACKWARD;
      break;
    default:
      setting = RELEASE;
      break;
  }

  m_motor->run(setting);

}

