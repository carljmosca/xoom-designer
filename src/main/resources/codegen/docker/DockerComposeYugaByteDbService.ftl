yb-master:
    image: yugabytedb/yugabyte:latest
    container_name: yb-master-n1
    volumes:
      - yb-master-data-1:/mnt/master
    command: [ "/home/yugabyte/bin/yb-master",
      "--fs_data_dirs=/mnt/master",
      "--master_addresses=yb-master-n1:7100",
      "--rpc_bind_addresses=yb-master-n1:7100",
      "--replication_factor=1"]
    ports:
      - "7000:7000"
    environment:
      SERVICE_7000_NAME: yb-master

  yb-tserver:
    image: yugabytedb/yugabyte:latest
    container_name: yb-tserver-n1
    volumes:
      - yb-tserver-data-1:/mnt/tserver
    command: [ "/home/yugabyte/bin/yb-tserver",
      "--fs_data_dirs=/mnt/tserver",
      "--start_pgsql_proxy",
      "--rpc_bind_addresses=yb-tserver-n1:9100",
      "--tserver_master_addrs=yb-master-n1:7100"]
    ports:
      - "9042:9042"
      - "5433:5433"
      - "9000:9000"
    environment:
      SERVICE_5433_NAME: ysql
      SERVICE_9042_NAME: ycql
      SERVICE_6379_NAME: yedis
      SERVICE_9000_NAME: yb-tserver
    depends_on:
      - yb-master