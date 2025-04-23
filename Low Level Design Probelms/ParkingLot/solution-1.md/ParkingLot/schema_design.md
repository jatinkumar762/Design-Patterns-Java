# Database Schema Design for Parking Lot System

Here's a comprehensive database table design for the parking lot system:

## 1. Core Tables

### Vehicle
```sql
CREATE TABLE Vehicle (
    vehicle_id VARCHAR(20) PRIMARY KEY,
    license_number VARCHAR(20) NOT NULL UNIQUE,
    vehicle_type ENUM('CAR', 'TRUCK', 'VAN', 'MOTORCYCLE', 'ELECTRIC') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### ParkingLot
```sql
CREATE TABLE ParkingLot (
    parking_lot_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    total_capacity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### ParkingFloor
```sql
CREATE TABLE ParkingFloor (
    floor_id INT AUTO_INCREMENT PRIMARY KEY,
    parking_lot_id INT NOT NULL,
    floor_name VARCHAR(20) NOT NULL,
    display_order INT NOT NULL,
    FOREIGN KEY (parking_lot_id) REFERENCES ParkingLot(parking_lot_id),
    UNIQUE (parking_lot_id, floor_name)
);
```

## 2. Parking Spot Tables

### ParkingSpotType
```sql
CREATE TABLE ParkingSpotType (
    spot_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(20) NOT NULL UNIQUE,
    description TEXT
);

-- Initial data
INSERT INTO ParkingSpotType (type_name, description) VALUES 
('COMPACT', 'For small cars'),
('LARGE', 'For trucks and vans'),
('HANDICAPPED', 'For handicapped vehicles'),
('MOTORCYCLE', 'For motorcycles'),
('ELECTRIC', 'For electric vehicles with charging');
```

### ParkingSpot
```sql
CREATE TABLE ParkingSpot (
    spot_id INT AUTO_INCREMENT PRIMARY KEY,
    floor_id INT NOT NULL,
    spot_type_id INT NOT NULL,
    spot_number VARCHAR(10) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_electric_charging BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (floor_id) REFERENCES ParkingFloor(floor_id),
    FOREIGN KEY (spot_type_id) REFERENCES ParkingSpotType(spot_type_id),
    UNIQUE (floor_id, spot_number)
);
```

## 3. Entrance/Exit Tables

### Entrance
```sql
CREATE TABLE Entrance (
    entrance_id INT AUTO_INCREMENT PRIMARY KEY,
    parking_lot_id INT NOT NULL,
    entrance_name VARCHAR(20) NOT NULL,
    location_description TEXT,
    FOREIGN KEY (parking_lot_id) REFERENCES ParkingLot(parking_lot_id),
    UNIQUE (parking_lot_id, entrance_name)
);
```

### Exit
```sql
CREATE TABLE Exit (
    exit_id INT AUTO_INCREMENT PRIMARY KEY,
    parking_lot_id INT NOT NULL,
    exit_name VARCHAR(20) NOT NULL,
    location_description TEXT,
    FOREIGN KEY (parking_lot_id) REFERENCES ParkingLot(parking_lot_id),
    UNIQUE (parking_lot_id, exit_name)
);
```

## 4. Ticket and Payment Tables

### Ticket
```sql
CREATE TABLE Ticket (
    ticket_id VARCHAR(36) PRIMARY KEY,
    vehicle_id VARCHAR(20) NOT NULL,
    spot_id INT NOT NULL,
    entrance_id INT NOT NULL,
    entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    exit_time TIMESTAMP NULL,
    is_paid BOOLEAN DEFAULT FALSE,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id),
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id),
    FOREIGN KEY (entrance_id) REFERENCES Entrance(entrance_id)
);
```

### PaymentType
```sql
CREATE TABLE PaymentType (
    payment_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(20) NOT NULL UNIQUE
);

-- Initial data
INSERT INTO PaymentType (type_name) VALUES 
('CASH'), 
('CREDIT_CARD'), 
('DEBIT_CARD'), 
('MOBILE_PAYMENT');
```

### Payment
```sql
CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id VARCHAR(36) NOT NULL,
    payment_type_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    card_last_four VARCHAR(4) NULL,
    transaction_reference VARCHAR(50) NULL,
    payment_location ENUM('EXIT', 'INFO_PORTAL', 'ELECTRIC_PANEL') NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id),
    FOREIGN KEY (payment_type_id) REFERENCES PaymentType(payment_type_id)
);
```

## 5. Display and Info Tables

### DisplayBoard
```sql
CREATE TABLE DisplayBoard (
    board_id INT AUTO_INCREMENT PRIMARY KEY,
    floor_id INT NULL,
    parking_lot_id INT NOT NULL,
    board_type ENUM('FLOOR', 'MAIN') NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (floor_id) REFERENCES ParkingFloor(floor_id),
    FOREIGN KEY (parking_lot_id) REFERENCES ParkingLot(parking_lot_id)
);
```

### DisplayBoardStatus
```sql
CREATE TABLE DisplayBoardStatus (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT NOT NULL,
    spot_type_id INT NOT NULL,
    available_spots INT NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES DisplayBoard(board_id),
    FOREIGN KEY (spot_type_id) REFERENCES ParkingSpotType(spot_type_id),
    UNIQUE (board_id, spot_type_id)
);
```

### InfoPortal
```sql
CREATE TABLE InfoPortal (
    portal_id INT AUTO_INCREMENT PRIMARY KEY,
    floor_id INT NOT NULL,
    location_description TEXT,
    FOREIGN KEY (floor_id) REFERENCES ParkingFloor(floor_id)
);
```

## 6. Electric Charging Tables

### ElectricPanel
```sql
CREATE TABLE ElectricPanel (
    panel_id INT AUTO_INCREMENT PRIMARY KEY,
    spot_id INT NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    last_maintenance_date DATE,
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);
```

### ChargingSession
```sql
CREATE TABLE ChargingSession (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id VARCHAR(36) NOT NULL,
    panel_id INT NOT NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP NULL,
    energy_consumed DECIMAL(10,2) DEFAULT 0.00,
    charging_cost DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id),
    FOREIGN KEY (panel_id) REFERENCES ElectricPanel(panel_id)
);
```

## 7. Audit Tables

### ParkingLotActivity
```sql
CREATE TABLE ParkingLotActivity (
    activity_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_type ENUM('ENTRY', 'EXIT', 'PAYMENT', 'SPOT_CHANGE') NOT NULL,
    ticket_id VARCHAR(36) NULL,
    vehicle_id VARCHAR(20) NULL,
    spot_id INT NULL,
    activity_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id),
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);
```

### SystemLog
```sql
CREATE TABLE SystemLog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    log_level ENUM('INFO', 'WARNING', 'ERROR', 'CRITICAL') NOT NULL,
    component VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Indexes for Performance

```sql
-- Indexes for faster lookups
CREATE INDEX idx_ticket_vehicle ON Ticket(vehicle_id);
CREATE INDEX idx_ticket_spot ON Ticket(spot_id);
CREATE INDEX idx_ticket_entry_time ON Ticket(entry_time);
CREATE INDEX idx_spot_floor ON ParkingSpot(floor_id);
CREATE INDEX idx_spot_availability ON ParkingSpot(is_available, spot_type_id);
CREATE INDEX idx_payment_ticket ON Payment(ticket_id);
CREATE INDEX idx_vehicle_license ON Vehicle(license_number);
```

## Sample Queries

1. Check available spots by type on a floor:
```sql
SELECT pt.type_name, COUNT(*) as available_spots
FROM ParkingSpot ps
JOIN ParkingSpotType pt ON ps.spot_type_id = pt.spot_type_id
WHERE ps.floor_id = 1 AND ps.is_available = TRUE
GROUP BY pt.type_name;
```

2. Get current parking status:
```sql
SELECT 
    pl.name as parking_lot,
    pf.floor_name,
    pt.type_name,
    COUNT(CASE WHEN ps.is_available THEN 1 END) as available,
    COUNT(*) as total
FROM ParkingSpot ps
JOIN ParkingFloor pf ON ps.floor_id = pf.floor_id
JOIN ParkingLot pl ON pf.parking_lot_id = pl.parking_lot_id
JOIN ParkingSpotType pt ON ps.spot_type_id = pt.spot_type_id
GROUP BY pl.name, pf.floor_name, pt.type_name
ORDER BY pf.display_order, pt.type_name;
```

3. Calculate parking fee for a ticket:
```sql
SELECT 
    t.ticket_id,
    v.license_number,
    TIMESTAMPDIFF(HOUR, t.entry_time, NOW()) as hours_parked,
    CASE 
        WHEN TIMESTAMPDIFF(HOUR, t.entry_time, NOW()) <= 1 THEN 4.0
        WHEN TIMESTAMPDIFF(HOUR, t.entry_time, NOW()) <= 3 THEN 4.0 + (TIMESTAMPDIFF(HOUR, t.entry_time, NOW()) - 1) * 3.5
        ELSE 4.0 + 2 * 3.5 + (TIMESTAMPDIFF(HOUR, t.entry_time, NOW()) - 3) * 2.5
    END as amount_due
FROM Ticket t
JOIN Vehicle v ON t.vehicle_id = v.vehicle_id
WHERE t.ticket_id = 'some-ticket-id' AND t.is_paid = FALSE;
```

This database design supports all the requirements of the parking lot system while maintaining data integrity and allowing for efficient queries. The schema can be extended with additional tables or columns as needed for specific business requirements.