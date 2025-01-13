CREATE TABLE ANSWER (
                        id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        voc_id                      BIGINT,
                        writer_id                   BIGINT,
                        title                       VARCHAR(300),
                        is_public_within_company    TINYINT(1),
                        complaint_classification_id INT,
                        complaint_type              VARCHAR(255),
                        complaint_category          VARCHAR(255),
                        processing_type             VARCHAR(255),
                        resolution_status           VARCHAR(255),
                        notification_method         VARCHAR(255),
                        is_same_complaint           TINYINT(1),
                        is_reoccurring              TINYINT(1),
                        content                     TEXT,
                        created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        hits                        INT DEFAULT 0
);


CREATE TABLE VOC_PROCESS (
                             id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
                             voc_id                   BIGINT,

                             status_code              VARCHAR(255),

                             voc_type_id              BIGINT,
                             voc_type_origin_name     VARCHAR(255),

                             main_receptionist_id     BIGINT,
                             sub_receptionist_id      BIGINT,

                             due_date                 TIMESTAMP,
                             due_date_change_reason   VARCHAR(1500),

                             organization_assigner_id BIGINT,
                             organization_id          BIGINT,
                             main_assigner_id         BIGINT,
                             sub_assigner_id          BIGINT,

                             manager_id               BIGINT,
                             withdrawal_reason        VARCHAR(255),
                             created_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE VOC_KRCC_GENERAL (
                                  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  krcc_reporter_id BIGINT,
                                  visibility       TINYINT(1),
                                  expected_effect  VARCHAR(2000),
                                  improvement_plan VARCHAR(2000)
);

CREATE TABLE VOC_KRCC_ANTI_CORRUPTION (
                                          id                                     BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          krcc_reporter_id                       BIGINT,
                                          representative                         TINYINT(1),
                                          visibility                             TINYINT(1),
                                          expected_effect                        VARCHAR(2000),
                                          improvement_plan                       VARCHAR(2000),
                                          agreed_investigation_by_related_agency TINYINT(1),
                                          agreed_investigation_by_other_agency   TINYINT(1),
                                          agreed_transfer_investigation          TINYINT(1),
                                          reportee_name                          VARCHAR(255),
                                          reportee_organization                  VARCHAR(255),
                                          reportee_department                    VARCHAR(255),
                                          reportee_address_id                    BIGINT,
                                          reportee_contact                       VARCHAR(255)
);

CREATE TABLE VOC (
                     id                            BIGINT AUTO_INCREMENT PRIMARY KEY,
                     voc_classification            VARCHAR(255),
                     voc_manual_general_id         BIGINT,
                     voc_manual_anti_corruption_id BIGINT,
                     voc_krcc_general_id           BIGINT,
                     voc_krcc_anti_corruption_id   BIGINT,
                     voc_number                    VARCHAR(255),
                     reporter_name                 VARCHAR(255),
                     reported_at                   DATE,
                     phone                         VARCHAR(255),
                     mobile                        VARCHAR(255),
                     fax                           VARCHAR(255),
                     email                         VARCHAR(800),
                     address_id                    BIGINT,
                     receipt_channel_id            BIGINT,
                     receipt_type                  VARCHAR(50),
                     title                         VARCHAR(500),
                     contents                      TEXT,
                     register_id                   BIGINT,
                     voc_process_id                BIGINT,
                     deleted                       TINYINT(1),

                     registered_at                 TIMESTAMP,
                     created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                     hits                          BIGINT DEFAULT 0,
                     number_of_reporters           BIGINT
);

CREATE TABLE VOC_PROGRESS_NOTI_TYPE_RELATION (
                                                 id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 voc_id            BIGINT,
                                                 notification_type VARCHAR(255)
);

CREATE TABLE VOC_RESULT_NOTI_TYPE_RELATION (
                                               id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               voc_id            BIGINT,
                                               notification_type VARCHAR(255)
);
