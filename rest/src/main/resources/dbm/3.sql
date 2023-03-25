drop table `Property`;
drop table `SystemProperty`;

create table `propertyIdentifiers` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT,
    `label` varchar(128) NOT NULL,
    PRIMARY KEY(`id`)
);

create table `property` (
    `id` int UNSIGNED NOT NULL,
    `uId` varchar(36),
    `value` varchar(16000),
    PRIMARY KEY(`id`, `uId`)
);

create table `arrayProperty` (
    `id` int UNSIGNED NOT NULL,
    `uId` varchar(36),
    `index` int,
    `value` varchar(16000),
    PRIMARY KEY(`id`, `uId`)
);

ALTER TABLE `property` ADD FOREIGN KEY (`id`) REFERENCES `propertyIdentifiers` (`id`);
ALTER TABLE `arrayProperty` ADD FOREIGN KEY (`id`) REFERENCES `propertyIdentifiers` (`id`);

CREATE INDEX idx_key ON `bundles`(`bId`);
CREATE INDEX idx_name ON `Player`(`lastKnownName`);
CREATE INDEX idx_label ON `propertyIdentifiers`(`label`)