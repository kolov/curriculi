FROM jplock/zookeeper

ADD init-zookeeper.sh /init-zookeeper.sh
CMD ["/bin/bash"]
ENTRYPOINT [ "/init-zookeeper.sh", "/opt/zookeeper/bin/zkCli.sh", "zookeeper:2181"]